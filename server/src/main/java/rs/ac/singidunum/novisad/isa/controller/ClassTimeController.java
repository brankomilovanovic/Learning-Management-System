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

import rs.ac.singidunum.novisad.isa.dto.ClassTimeDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTeachingDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.ClassTime;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.service.ClassTimeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/classTimes")
public class ClassTimeController {

	@Autowired
	private ClassTimeService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<ClassTimeDTO>> getAll() {
		ArrayList<ClassTimeDTO> classTimeDTO = new ArrayList<ClassTimeDTO>();
		for(ClassTime classTime : service.findAll()) {

			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: classTime.getSubjectRealization().getSubject().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			
			classTimeDTO.add(new ClassTimeDTO(classTime.getId(), classTime.getVremePocetka(), classTime.getVremeKraja(),
					
					new TopicDTO(classTime.getTopic().getId(), classTime.getTopic().getOpis(), new TypeOfTopicDTO(classTime.getTopic().getTopicType().getId(), classTime.getTopic().getTopicType().getNaziv())), 
					new TypeOfTeachingDTO(classTime.getTypeOfTeaching().getId(), classTime.getTypeOfTeaching().getNaziv()),
					
					new SubjectRealizationDTO(classTime.getSubjectRealization().getId(), 
							new SubjectDTO(classTime.getSubjectRealization().getSubject().getId(), classTime.getSubjectRealization().getSubject().getNaziv(), classTime.getSubjectRealization().getSubject().getEspb(), classTime.getSubjectRealization().getSubject().isObavezan(), 
									classTime.getSubjectRealization().getSubject().getBrojPredavanja(), classTime.getSubjectRealization().getSubject().getBrojVezbi(), classTime.getSubjectRealization().getSubject().getDrugiObliciNastave(), classTime.getSubjectRealization().getSubject().getIstrazivackiRad(), 
									classTime.getSubjectRealization().getSubject().getOstaliCasovi(), null, topicsDTO, null))));
		}
		return new ResponseEntity<Iterable<ClassTimeDTO>>(classTimeDTO, HttpStatus.OK);
	}
	
	@GetMapping("/findByUsername/{username}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<ClassTimeDTO>> findByUsername(@PathVariable("username") String username) {

		ArrayList<ClassTimeDTO> classTimeDTO = new ArrayList<ClassTimeDTO>();
		for(ClassTime classTime : service.findByUsername(username)) {

			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: classTime.getSubjectRealization().getSubject().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			
			classTimeDTO.add(new ClassTimeDTO(classTime.getId(), classTime.getVremePocetka(), classTime.getVremeKraja(),
					
					new TopicDTO(classTime.getTopic().getId(), classTime.getTopic().getOpis(), new TypeOfTopicDTO(classTime.getTopic().getTopicType().getId(), classTime.getTopic().getTopicType().getNaziv())), 
					new TypeOfTeachingDTO(classTime.getTypeOfTeaching().getId(), classTime.getTypeOfTeaching().getNaziv()),
					
					new SubjectRealizationDTO(classTime.getSubjectRealization().getId(), 
							new SubjectDTO(classTime.getSubjectRealization().getSubject().getId(), classTime.getSubjectRealization().getSubject().getNaziv(), classTime.getSubjectRealization().getSubject().getEspb(), classTime.getSubjectRealization().getSubject().isObavezan(), 
									classTime.getSubjectRealization().getSubject().getBrojPredavanja(), classTime.getSubjectRealization().getSubject().getBrojVezbi(), classTime.getSubjectRealization().getSubject().getDrugiObliciNastave(), classTime.getSubjectRealization().getSubject().getIstrazivackiRad(), 
									classTime.getSubjectRealization().getSubject().getOstaliCasovi(), username, topicsDTO, null))));
		}
		return new ResponseEntity<Iterable<ClassTimeDTO>>(classTimeDTO, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<ClassTime> create(@RequestBody ClassTime object){
	    try {
	    	service.save(object);
			return new ResponseEntity<ClassTime>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<ClassTime>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<ClassTime> update(@PathVariable("id") Long id, @RequestBody ClassTime changedObject) {
		ClassTime objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<ClassTime>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<ClassTime>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<ClassTime> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<ClassTime>(HttpStatus.OK);
		}
		return new ResponseEntity<ClassTime>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsBySubjectID/{objectToChangeID}/{id}/{typeTeachingId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsBySubjectID(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("id") Long id, @PathVariable("typeTeachingId") Long typeTeachingId) {
		if (service.existsBySubjectID(id, typeTeachingId) == true) {
			ClassTime objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getSubjectRealization().getSubject().getId().equals(id)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Class time already exists with that type teaching for this subject!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Class time already exists with that type teaching for this subject!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
