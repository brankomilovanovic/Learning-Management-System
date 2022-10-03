package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
import rs.ac.singidunum.novisad.isa.service.StudentService;
import rs.ac.singidunum.novisad.isa.service.StudentServiceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/studentservice")
public class StudentServiceController {
	
	@Autowired
	private StudentServiceService st;
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/svistudentiservice")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<StudentServicesDTO>> dobavi() {
		ArrayList<StudentServicesDTO> gosti = new ArrayList<StudentServicesDTO>();
	
			for(StudentServices student : st.findAll()) {
				
				//Set<YearOfStudyDTO> year =new HashSet<>();
				Set<YearOfStudyDTO> years =new HashSet<>();
				Set<YearOfStudyDTO> yearfor =new HashSet<>();
				StudentOnTheYearDTO studentyear = new StudentOnTheYearDTO();
				
				StudentOnTheYear services = student.getStudent(); 
				
				Set<TakingExamDTO> takingExams =new HashSet<>();
				for(TakingExam takingExam : services.getTakingExam()) {
					takingExams.add(new TakingExamDTO(takingExam.getId(), takingExam.getBodovi(), takingExam.getNapomena(), null,
							new EvaluationKnowledgeDTO(takingExam.getEvaluationKnowledge().getId(), null, null, takingExam.getEvaluationKnowledge().getBodovi(), null, null, null, null)));
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
	
	//DOBAVLJANJE PREDMETA SA GODINA STUDIJSKIH PROGRAMA KOJE JE STUDENT UPISAO
		@GetMapping("/following/{username}")
		public ResponseEntity<Iterable<StudentServicesDTO>> getAll(@PathVariable("username") String username) {
			ArrayList<StudentServicesDTO> gosti = new ArrayList<StudentServicesDTO>();
			
			for(StudentServices student : studentService.findBySub(username)) {
					
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
		
		//DOBAVLJANJE PREDMETA SA GODINA STUDIJSKIH PROGRAMA KOJE JE STUDENT UPISAO
				@GetMapping("/following_id/{id}")
				public ResponseEntity<Iterable<StudentServicesDTO>> getAllStudentsWithId(@PathVariable("id") Long id) {
					ArrayList<StudentServicesDTO> gosti = new ArrayList<StudentServicesDTO>();
					
					for(StudentServices student : st.findById(id)) {
							
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
	@PostMapping("/createstudentservice")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentServices> create(@RequestBody StudentServices yearOfStudy){
	    try {
	    	st.save(yearOfStudy);
			return new ResponseEntity<StudentServices>(yearOfStudy, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<StudentServices>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentServices> update(@PathVariable("id") Long typeRanksId, @RequestBody StudentServices changedTypeRanks) {
		StudentServices typeRanks = st.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			st.save(changedTypeRanks);
			return new ResponseEntity<StudentServices>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<StudentServices>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentServices> deleteKupovina(@PathVariable("id") Long typeRanksId) {
		if (st.findOne(typeRanksId).isPresent()) {
			st.delete(typeRanksId);
			return new ResponseEntity<StudentServices>(HttpStatus.OK);
		}
		return new ResponseEntity<StudentServices>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsStudentByStudyProgrammeAndYear/{objectToChangeID}/{studentID}/{studyProgrammeId}/{yearOfStudyId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsStudentByStudyProgrammeAndYear(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("studentID") Long studentID, @PathVariable("studyProgrammeId") Long studyProgrammeId, @PathVariable("yearOfStudyId") Long yearOfStudyId) {
		if (st.existsStudentByStudyProgrammeAndYear(studentID, studyProgrammeId, yearOfStudyId) == true) {
			StudentServices objectToChange = st.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				for(YearOfStudy yearOfStudy: objectToChange.getYear()) {
					if(objectToChange.getStudent().getId().equals(studentID) && objectToChange.getStudyProgrammes().getId().equals(studyProgrammeId) && yearOfStudy.getId().equals(yearOfStudyId)) {
						return ResponseEntity.ok(HttpStatus.OK);
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Student is already following this study program this year!"));
					}
				}

			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Student is already following this study program this year!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
