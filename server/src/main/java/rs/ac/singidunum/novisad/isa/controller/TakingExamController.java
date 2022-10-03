package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;

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
import rs.ac.singidunum.novisad.isa.dto.StudentDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentOnTheYearDTO;
import rs.ac.singidunum.novisad.isa.dto.TakingExamDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeEvaluationDTO;
import rs.ac.singidunum.novisad.isa.model.TakingExam;
import rs.ac.singidunum.novisad.isa.service.TakingExamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/takingExam")
public class TakingExamController {

	@Autowired
	TakingExamService service;
	
	@GetMapping("/allTakingExam")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TakingExamDTO>> getAll() {
		ArrayList<TakingExamDTO> takingExams = new ArrayList<TakingExamDTO>();
		for (TakingExam takingExam : service.findAll()) {

			takingExams.add(new TakingExamDTO(takingExam.getId(), takingExam.getBodovi(), takingExam.getNapomena(), 
					new StudentOnTheYearDTO(takingExam.getStudentOnTheYear().getId(), takingExam.getStudentOnTheYear().getIndexNo(), takingExam.getStudentOnTheYear().getDateOfEntry(),
							null, new StudentDTO(takingExam.getStudentOnTheYear().getStudent().getId(), takingExam.getStudentOnTheYear().getStudent().getJmbg(), 
									takingExam.getStudentOnTheYear().getStudent().getDateOfBirth(), takingExam.getStudentOnTheYear().getStudent().getAddress(), takingExam.getStudentOnTheYear().getStudent().getPhoneNumber()), null), 
					new EvaluationKnowledgeDTO(null, takingExam.getEvaluationKnowledge().getVremePocetka(), null, takingExam.getEvaluationKnowledge().getBodovi(), null, null, 
							new TypeEvaluationDTO(takingExam.getEvaluationKnowledge().getTypeEvaluation().getId(), takingExam.getEvaluationKnowledge().getTypeEvaluation().getTipEvaluacije()), null)));
		}

		return new ResponseEntity<Iterable<TakingExamDTO>>(takingExams, HttpStatus.OK);
	}
	
	@GetMapping("/findByStudentOnTheYearAndSubject/{studentOnTheYear}/{subject}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TakingExamDTO>> findByStudentOnTheYearAndSubject(@PathVariable("studentOnTheYear") Long studentOnTheYearID, @PathVariable("subject") Long subjectID) {
		ArrayList<TakingExamDTO> takingExams = new ArrayList<TakingExamDTO>();
		for (TakingExam takingExam : service.findByStudentOnTheYearAndSubject(studentOnTheYearID, subjectID)) {
			takingExams.add(new TakingExamDTO(takingExam.getId(), takingExam.getBodovi(), takingExam.getNapomena(), 
					new StudentOnTheYearDTO(takingExam.getStudentOnTheYear().getId(), takingExam.getStudentOnTheYear().getIndexNo(), takingExam.getStudentOnTheYear().getDateOfEntry(),
							null, new StudentDTO(takingExam.getStudentOnTheYear().getStudent().getId(), takingExam.getStudentOnTheYear().getStudent().getJmbg(), 
									takingExam.getStudentOnTheYear().getStudent().getDateOfBirth(), takingExam.getStudentOnTheYear().getStudent().getAddress(), takingExam.getStudentOnTheYear().getStudent().getPhoneNumber()), null), 
					new EvaluationKnowledgeDTO(null, takingExam.getEvaluationKnowledge().getVremePocetka(), null, takingExam.getEvaluationKnowledge().getBodovi(), null, null, 
							new TypeEvaluationDTO(takingExam.getEvaluationKnowledge().getTypeEvaluation().getId(), takingExam.getEvaluationKnowledge().getTypeEvaluation().getTipEvaluacije()), null)));
		}

		return new ResponseEntity<Iterable<TakingExamDTO>>(takingExams, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<TakingExam> create(@RequestBody TakingExam object){
	    try {
			service.save(object);
			return new ResponseEntity<TakingExam>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TakingExam>(HttpStatus.BAD_REQUEST);
	}

	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TakingExam> update(@PathVariable("id") Long id, @RequestBody TakingExam changedObject) {
		TakingExam objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<TakingExam>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<TakingExam>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TakingExam> delete(@PathVariable("id") Long id) {		
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<TakingExam>(HttpStatus.OK);
		}
		return new ResponseEntity<TakingExam>(HttpStatus.NOT_FOUND);
	}
}
