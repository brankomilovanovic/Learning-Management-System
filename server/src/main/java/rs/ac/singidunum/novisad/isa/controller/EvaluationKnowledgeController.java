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

import rs.ac.singidunum.novisad.isa.dto.EvaluationInstrumentDTO;
import rs.ac.singidunum.novisad.isa.dto.EvaluationKnowledgeDTO;
import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeEvaluationDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.EvaluationKnowledge;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.service.EvaluationKnowledgeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/evaluationKnowledges")
public class EvaluationKnowledgeController {
	
	@Autowired
	private EvaluationKnowledgeService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<EvaluationKnowledgeDTO>> getAll() {
		ArrayList<EvaluationKnowledgeDTO> evaluationKnowledges = new ArrayList<EvaluationKnowledgeDTO>();
		for(EvaluationKnowledge evaluationKnowledge : service.findAll()) {
			
		/////SUBJECT
		Set<TopicDTO> topicsDTO = new HashSet<>();
		for(Topic topic: evaluationKnowledge.getSubjectRealization().getSubject().getTopic()) { 
			topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
		}
		SubjectDTO subjectDTO = new SubjectDTO(evaluationKnowledge.getSubjectRealization().getSubject().getId(), evaluationKnowledge.getSubjectRealization().getSubject().getNaziv(), evaluationKnowledge.getSubjectRealization().getSubject().getEspb(), 
				evaluationKnowledge.getSubjectRealization().getSubject().isObavezan(),evaluationKnowledge.getSubjectRealization().getSubject().getBrojPredavanja(), evaluationKnowledge.getSubjectRealization().getSubject().getBrojVezbi(), 
				evaluationKnowledge.getSubjectRealization().getSubject().getDrugiObliciNastave(), evaluationKnowledge.getSubjectRealization().getSubject().getIstrazivackiRad(), evaluationKnowledge.getSubjectRealization().getSubject().getOstaliCasovi(), null, null, null);
					
					
			evaluationKnowledges.add(new EvaluationKnowledgeDTO(evaluationKnowledge.getId(), evaluationKnowledge.getVremePocetka(), evaluationKnowledge.getVremeKraja(),
					evaluationKnowledge.getBodovi(), new TopicDTO(evaluationKnowledge.getTopic().getId(), evaluationKnowledge.getTopic().getOpis(), 
															new TypeOfTopicDTO(evaluationKnowledge.getTopic().getTopicType().getId(), evaluationKnowledge.getTopic().getTopicType().getNaziv())), 
					
					new EvaluationInstrumentDTO(evaluationKnowledge.getEvaluationInstrument().getId(), evaluationKnowledge.getEvaluationInstrument().getTipTestiranja(), 
							new FileDTO(evaluationKnowledge.getEvaluationInstrument().getFile().getId(), evaluationKnowledge.getEvaluationInstrument().getFile().getOpis(), evaluationKnowledge.getEvaluationInstrument().getFile().getUrl())), 
							
						new TypeEvaluationDTO(evaluationKnowledge.getTypeEvaluation().getId(), evaluationKnowledge.getTypeEvaluation().getTipEvaluacije()), 
						new SubjectRealizationDTO(evaluationKnowledge.getSubjectRealization().getId(), subjectDTO)));
								
		}	
		return new ResponseEntity<Iterable<EvaluationKnowledgeDTO>>(evaluationKnowledges, HttpStatus.OK);
	}
	
	@GetMapping("/getAllUndoneTests/{studentOnTheYearId}/{subjectId}")
	public ResponseEntity<Iterable<EvaluationKnowledgeDTO>> getAllUndoneTests(@PathVariable("studentOnTheYearId") Long studentOnTheYearId, @PathVariable("subjectId") Long subjectId) {
		ArrayList<EvaluationKnowledgeDTO> evaluationKnowledges = new ArrayList<EvaluationKnowledgeDTO>();
		for(EvaluationKnowledge evaluationKnowledge : service.findAllUndoneTests(studentOnTheYearId, subjectId)) {
			
		/////SUBJECT
		Set<TopicDTO> topicsDTO = new HashSet<>();
		for(Topic topic: evaluationKnowledge.getSubjectRealization().getSubject().getTopic()) { 
			topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
		}
		SubjectDTO subjectDTO = new SubjectDTO(evaluationKnowledge.getSubjectRealization().getSubject().getId(), evaluationKnowledge.getSubjectRealization().getSubject().getNaziv(), evaluationKnowledge.getSubjectRealization().getSubject().getEspb(), 
				evaluationKnowledge.getSubjectRealization().getSubject().isObavezan(),evaluationKnowledge.getSubjectRealization().getSubject().getBrojPredavanja(), evaluationKnowledge.getSubjectRealization().getSubject().getBrojVezbi(), 
				evaluationKnowledge.getSubjectRealization().getSubject().getDrugiObliciNastave(), evaluationKnowledge.getSubjectRealization().getSubject().getIstrazivackiRad(), evaluationKnowledge.getSubjectRealization().getSubject().getOstaliCasovi(), null, null, null);
					
					
			evaluationKnowledges.add(new EvaluationKnowledgeDTO(evaluationKnowledge.getId(), evaluationKnowledge.getVremePocetka(), evaluationKnowledge.getVremeKraja(),
					evaluationKnowledge.getBodovi(), new TopicDTO(evaluationKnowledge.getTopic().getId(), evaluationKnowledge.getTopic().getOpis(), 
															new TypeOfTopicDTO(evaluationKnowledge.getTopic().getTopicType().getId(), evaluationKnowledge.getTopic().getTopicType().getNaziv())), 
					
					new EvaluationInstrumentDTO(evaluationKnowledge.getEvaluationInstrument().getId(), evaluationKnowledge.getEvaluationInstrument().getTipTestiranja(), 
							new FileDTO(evaluationKnowledge.getEvaluationInstrument().getFile().getId(), evaluationKnowledge.getEvaluationInstrument().getFile().getOpis(), evaluationKnowledge.getEvaluationInstrument().getFile().getUrl())), 
							
						new TypeEvaluationDTO(evaluationKnowledge.getTypeEvaluation().getId(), evaluationKnowledge.getTypeEvaluation().getTipEvaluacije()), 
						new SubjectRealizationDTO(evaluationKnowledge.getSubjectRealization().getId(), subjectDTO)));
								
		}	
		return new ResponseEntity<Iterable<EvaluationKnowledgeDTO>>(evaluationKnowledges, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EvaluationKnowledge> create(@RequestBody EvaluationKnowledge object){
	    try {
	    	service.save(object);
			return new ResponseEntity<EvaluationKnowledge>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<EvaluationKnowledge>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EvaluationKnowledge> update(@PathVariable("id") Long id, @RequestBody EvaluationKnowledge changedObject) {
		EvaluationKnowledge objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<EvaluationKnowledge>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<EvaluationKnowledge>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EvaluationKnowledge> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<EvaluationKnowledge>(HttpStatus.OK);
		}
		return new ResponseEntity<EvaluationKnowledge>(HttpStatus.NOT_FOUND);
	}
}
