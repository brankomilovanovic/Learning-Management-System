package rs.ac.singidunum.novisad.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.StudentDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentServicesDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectNotificationsDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.YearOfStudyDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Student;
import rs.ac.singidunum.novisad.isa.model.StudentServices;
import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;
import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.service.StudentService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	StudentService studentService;

	@Autowired
	UserService userService;
	
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	Date dateOfBirth;
	
	@GetMapping("/allStudents")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<StudentDTO>> getAllStudents() {
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>();
		for (Student student : studentService.findAll()) {
			students.add(new StudentDTO(student.getId(), student.getJmbg(), student.getDateOfBirth(), 
					student.getAddress(), student.getPhoneNumber(),new UserDTO(student.getUser().getId(), student.getUser().getName(), student.getUser().getSurname(), null, null, null, null)));
		}

		return new ResponseEntity<Iterable<StudentDTO>>(students, HttpStatus.OK);
	}
	
	//DOBAVLJANJE PREDMETA SA GODINA STUDIJSKIH PROGRAMA KOJE JE STUDENT UPISAO
	@GetMapping("/following/{username}")
	public ResponseEntity<Iterable<StudentServicesDTO>> getAll(@PathVariable("username") String username) {
		ArrayList<StudentServicesDTO> gosti = new ArrayList<StudentServicesDTO>();
		
		for(StudentServices student : studentService.findBySub(username)) {
				
			//DODATI DA VRACA I PREDMETE NA GODINI STUDIJA
			Set<YearOfStudyDTO> yearfor =new HashSet<>();
			for(YearOfStudy e:student.getYear()) {
				Set<SubjectDTO> subjectDTO = new HashSet<>();
				for(rs.ac.singidunum.novisad.isa.model.Subject s:e.getSubjects()) {
					subjectDTO.add(new SubjectDTO(s.getId(),s.getNaziv(),s.getEspb(),s.isObavezan(),s.getBrojPredavanja()
                			,s.getBrojVezbi(),s.getDrugiObliciNastave(),s.getIstrazivackiRad(),s.getOstaliCasovi(), s.getSilabus(), null, null));
				}
				yearfor.add(new YearOfStudyDTO(e.getId(), e.getYear(), e.isActive(), subjectDTO, null, null));
			}
			
			
			gosti.add(new StudentServicesDTO(student.getId(), null, null, yearfor, student.isChoosed()));
			
		}
		
	return new ResponseEntity<Iterable<StudentServicesDTO>>(gosti, HttpStatus.OK);
		
	}
	
	@GetMapping("/{userId}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable("userId") Long userId) {
		Optional<Student> student = studentService.findByUserId(userId);
		StudentDTO studentDTO = new StudentDTO();	
		if (student.isPresent()) {
			studentDTO = new StudentDTO(student.get().getId(), student.get().getJmbg(), student.get().getDateOfBirth(), 
					student.get().getAddress(), student.get().getPhoneNumber());
			
			Set<SubjectNotificationsDTO> subjectNotificationsDTO = new HashSet<SubjectNotificationsDTO>();
			for(SubjectNotifications object : student.get().getSubjectNotifications()) {
				subjectNotificationsDTO.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), null, null));
			}
			studentDTO.setUser(new UserDTO(student.get().getUser().getId(), null, null, null, null, null, null));
			studentDTO.setSubjectNotifications(subjectNotificationsDTO);
			
			return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
		}
		return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getByUsername/{username}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentDTO> getByUsername(@PathVariable("username") String username) {
		Optional<Student> student = studentService.findByUsername(username);
		StudentDTO studentDTO = new StudentDTO();	
		if (student.isPresent()) {
			studentDTO = new StudentDTO(student.get().getId(), student.get().getJmbg(), student.get().getDateOfBirth(), 
					student.get().getAddress(), student.get().getPhoneNumber());
			
			Set<SubjectNotificationsDTO> subjectNotificationsDTO = new HashSet<SubjectNotificationsDTO>();
			for(SubjectNotifications object : student.get().getSubjectNotifications()) {
				subjectNotificationsDTO.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), null, null));
			}
			studentDTO.setUser(new UserDTO(student.get().getUser().getId(), null, null, null, null, null, null));
			studentDTO.setSubjectNotifications(subjectNotificationsDTO);
			
			return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
		}
		return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/createStudent")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Student> createStudent(@RequestBody Student newStudent){
		try {
			Optional<User> user = userService.findByUsername(newStudent.getUser().getUsername());
			newStudent.setUser(user.get());
			studentService.save(newStudent);
			return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{userId}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Student> updateStudent(@PathVariable("userId") Long userId, @RequestBody Student student) {
		Optional<Student> studentExist = studentService.findByUserId(userId);
		if(studentExist != null) {
			studentService.save(student);
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{userId}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentDTO> deleteStudent(@PathVariable("userId") Long userId) {
		Optional<Student> student = studentService.findByUserId(userId);
		if (student.isPresent()) {
			studentService.delete(student.get().getId());
			return new ResponseEntity<StudentDTO>(HttpStatus.OK);
		}
		return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/checkJmbg/{userId}/{jmbg}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> checkJmbg(@PathVariable("userId") String userId, @PathVariable("jmbg") String jmbg) {
		if (studentService.existByJmbg(jmbg) == true) {
			if(!userId.equals("null")) {
				try {
					Optional<Student> student = studentService.findOne(Long.parseLong(userId));
					if(!jmbg.equals(student.get().getJmbg())) { return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!")); }
				} 
				catch(NoSuchElementException e) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!"));	
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("JMBG is free!"));
	}
	
	@PostMapping("/read_notification")
	public ResponseEntity<Student> userReadNotification(@RequestBody Student student) {
		Optional<Student> studentExist = studentService.findByUserId(student.getId());
		if(studentExist != null) {
			studentService.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
	}

}
