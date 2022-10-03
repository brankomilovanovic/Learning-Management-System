package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.EvaluationKnowledgeDTO;
import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentOnTheYearDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentServicesDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentTestsDTO;
import rs.ac.singidunum.novisad.isa.dto.StudyProgrammeDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.TakingExamDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeEvaluationDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.YearOfStudyDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.StudentOnTheYear;
import rs.ac.singidunum.novisad.isa.model.StudentServices;
import rs.ac.singidunum.novisad.isa.model.StudentTests;
import rs.ac.singidunum.novisad.isa.model.StudyProgramme;
import rs.ac.singidunum.novisad.isa.model.TakingExam;
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.service.StudentOnTheYearService;
import rs.ac.singidunum.novisad.isa.service.StudentServiceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/studentnagodini")
public class StudentOnTheYearController {

	@Autowired
	private StudentOnTheYearService st;
	
	@Autowired
	private StudentServiceService e;
	
	@GetMapping("/svinagodini")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<StudentOnTheYearDTO>> dobavi() {
		ArrayList<StudentOnTheYearDTO> students = new ArrayList<StudentOnTheYearDTO>();
		for(StudentOnTheYear p : st.findAll()) {
			
			Set<StudentTestsDTO> studentTests = new HashSet<>();
			for(StudentTests studentTest: p.getStudentTests()) {
				studentTests.add(new StudentTestsDTO(studentTest.getId(), studentTest.getKolokvijum1(), studentTest.getKolokvijum2(), studentTest.getIspit(), studentTest.getAktivnost(),
						studentTest.getOcena(), null, new SubjectDTO(studentTest.getSubject().getId(), null, 0, false, 0, 0, 0, 0, 0, null, null, null)));
			}
			
			students.add(new StudentOnTheYearDTO(p.getId(),p.getIndexNo(),p.getDateOfEntry(),null,
					new StudentDTO(p.getStudent().getId(),p.getStudent().getJmbg(),p.getStudent().getDateOfBirth(),
							p.getStudent().getAddress(),p.getStudent().getPhoneNumber(),
							new UserDTO(null, p.getStudent().getUser().getName(), p.getStudent().getUser().getSurname(), "AAAA", null, null, null)), studentTests));
		}
		return new ResponseEntity<Iterable<StudentOnTheYearDTO>>(students, HttpStatus.OK);
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<StudentOnTheYearDTO> getone(@PathVariable("id") Long id) {
		Optional<StudentOnTheYear> student = st.findStudent(id);
		StudentOnTheYearDTO studentOnTheYearDTO = new StudentOnTheYearDTO();
		if(student.isPresent()) {
			Set<StudentTestsDTO> studentTests = new HashSet<>();
			for(StudentTests studentTest: student.get().getStudentTests()) {
				studentTests.add(new StudentTestsDTO(studentTest.getId(), studentTest.getKolokvijum1(), studentTest.getKolokvijum2(), studentTest.getIspit(), studentTest.getAktivnost(),
						studentTest.getOcena(), null, new SubjectDTO(studentTest.getSubject().getId(),studentTest.getSubject().getNaziv() , studentTest.getSubject().getEspb(), studentTest.getSubject().isObavezan(), 0, 0, 0, 0, 0, null, null, null)));
			}
			
			
			StudentDTO s = new StudentDTO();
			s = new StudentDTO(student.get().getStudent().getId(), student.get().getStudent().getJmbg(),
					student.get().getStudent().getDateOfBirth(), student.get().getStudent().getAddress(), student.get().getStudent().getPhoneNumber());
			
			studentOnTheYearDTO= new StudentOnTheYearDTO(student.get().getId(), student.get().getIndexNo(), student.get().getDateOfEntry(), null, s, studentTests);
			return new ResponseEntity<StudentOnTheYearDTO>(studentOnTheYearDTO, HttpStatus.OK);
		}
		return new ResponseEntity<StudentOnTheYearDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/upisi/{id}")
	public ResponseEntity<Iterable<StudentServicesDTO>> geto(@PathVariable("id") Long id) {
		ArrayList<StudentServicesDTO> gosti = new ArrayList<StudentServicesDTO>();
		for(StudentServices student : e.findById(id)) {
			
			//Set<YearOfStudyDTO> year =new HashSet<>();
			Set<YearOfStudyDTO> years =new HashSet<>();
			Set<YearOfStudyDTO> yearfor =new HashSet<>();
			StudentOnTheYearDTO studentyear = new StudentOnTheYearDTO();
			StudentOnTheYear services = student.getStudent(); 
			
			Set<TakingExamDTO> takingExams =new HashSet<>();
			for(TakingExam takingExam : services.getTakingExam()) {
				takingExams.add(new TakingExamDTO(takingExam.getId(), takingExam.getBodovi(), takingExam.getNapomena(), null,
						new EvaluationKnowledgeDTO(takingExam.getEvaluationKnowledge().getId(), takingExam.getEvaluationKnowledge().getVremePocetka(), null, takingExam.getEvaluationKnowledge().getBodovi(), null, null, 
								new TypeEvaluationDTO(takingExam.getEvaluationKnowledge().getTypeEvaluation().getId(), takingExam.getEvaluationKnowledge().getTypeEvaluation().getTipEvaluacije()), null)));
			}

			Set<StudentTestsDTO> studentTests = new HashSet<>();
			for(StudentTests studentTest: services.getStudentTests()) {
				studentTests.add(new StudentTestsDTO(studentTest.getId(), studentTest.getKolokvijum1(), studentTest.getKolokvijum2(), studentTest.getIspit(), studentTest.getAktivnost(),
						studentTest.getOcena(), null, new SubjectDTO(studentTest.getSubject().getId(), null, 0, false, 0, 0, 0, 0, 0, null, null, null)));
			}
			
				studentyear = new StudentOnTheYearDTO(services.getId(),services.getIndexNo(), services.getDateOfEntry(), takingExams,
						new StudentDTO(services.getStudent().getId(), services.getStudent().getJmbg(),services.getStudent().getDateOfBirth(),
							services.getStudent().getAddress(),services.getStudent().getPhoneNumber(),
						new UserDTO(services.getStudent().getUser().getId(), services.getStudent().getUser().getName(), services.getStudent().getUser().getSurname(), null, null, null, null)), studentTests);
			
			
			StudyProgrammeDTO studyProgramme = new StudyProgrammeDTO();
			StudyProgramme programme= student.getStudyProgrammes();
				studyProgramme=new StudyProgrammeDTO(programme.getId(),programme.getName(),
						new ProfessorDTO(programme.getDirector().getId(),programme.getDirector().getJmbg(),
								programme.getDirector().getDateOfBirth(),programme.getDirector().getAddress()
								,programme.getDirector().getPhoneNumber(),programme.getDirector().getBiography(),
							null), years);
				
			for(YearOfStudy y:programme.getYearOfStudy()) {
				
					years.add(new YearOfStudyDTO(y.getId(), y.getYear(), y.isActive(), null, null, null));
					
			}
			//DODATI DA VRACA I PREDMETE NA GODINI STUDIJA
			for(YearOfStudy e:student.getYear()) {
				Set<SubjectDTO> subjectDTO = new HashSet<>();
				for(rs.ac.singidunum.novisad.isa.model.Subject s:e.getSubjects()) {
					subjectDTO.add(new SubjectDTO(s.getId(),s.getNaziv(),s.getEspb(),s.isObavezan(),s.getBrojPredavanja()
                			,s.getBrojVezbi(),s.getDrugiObliciNastave(),s.getIstrazivackiRad(),s.getOstaliCasovi(), s.getSilabus(), null, null));
				}
				yearfor.add(new YearOfStudyDTO(e.getId(), e.getYear(), e.isActive(), subjectDTO, null, null));
			}
			
			gosti.add(new StudentServicesDTO(student.getId(), studentyear, studyProgramme, yearfor, student.isChoosed()));
		}
		
	return new ResponseEntity<Iterable<StudentServicesDTO>>(gosti, HttpStatus.OK);
	}
	
	@PostMapping("/createstudentonyear")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentOnTheYear> create(@RequestBody StudentOnTheYear yearOfStudy){
	    try {
	    	st.save(yearOfStudy);
			return new ResponseEntity<StudentOnTheYear>(yearOfStudy, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<StudentOnTheYear>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentOnTheYear> update(@PathVariable("id") Long typeRanksId, @RequestBody StudentOnTheYear changedTypeRanks) {
		StudentOnTheYear typeRanks = st.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			st.save(changedTypeRanks);
			return new ResponseEntity<StudentOnTheYear>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<StudentOnTheYear>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentOnTheYear> deleteKupovina(@PathVariable("id") Long typeRanksId) {
		if (st.findOne(typeRanksId).isPresent()) {
			st.delete(typeRanksId);
			return new ResponseEntity<StudentOnTheYear>(HttpStatus.OK);
		}
		return new ResponseEntity<StudentOnTheYear>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/existIndex/{objectToChangeID}/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existIndex(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("id") String id) {
		if (st.existIndex(id) == true) {
			StudentOnTheYear objectToChange = st.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getIndexNo().equals(id)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: This index no already exists!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: This index no already exists!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("This index no don't exists!"));
	}
	
	@GetMapping("/existsStudent/{objectToChangeID}/{studentId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsStudent(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("studentId") Long studentId) {
		if (st.existsStudent(studentId) == true) {
			StudentOnTheYear objectToChange = st.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getStudent().getId().equals(studentId)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Student already have index!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Student already have index!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Student don't have index!"));
	}
}
