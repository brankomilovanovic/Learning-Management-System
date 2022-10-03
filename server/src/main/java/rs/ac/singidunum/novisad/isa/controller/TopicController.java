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

import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.TeachingMaterialDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.TypeOfTopic.TypeTopic;
import rs.ac.singidunum.novisad.isa.service.SubjectService;
import rs.ac.singidunum.novisad.isa.service.TopicService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topics")
public class TopicController {

	@Autowired
	TopicService service;
	
	@Autowired
	SubjectService subjectService;
	
	@GetMapping("/allTopics")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TopicDTO>> getAll() {
		ArrayList<TopicDTO> topics = new ArrayList<TopicDTO>();
		
		for (Topic topic : service.findAll()) {
			TopicDTO newTopic = new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv()));
			
			if(newTopic.getTopicType().getNaziv().equals(TypeTopic.TERMIN_NASTAVE)) {
			newTopic.setTeachingMaterial(new TeachingMaterialDTO(topic.getTeachingMaterial().getId(), topic.getTeachingMaterial().getNaziv(), topic.getTeachingMaterial().getGodinaIzdavanja(), topic.getTeachingMaterial().getAutori(), 
					new FileDTO(topic.getTeachingMaterial().getFile().getId(), topic.getTeachingMaterial().getFile().getOpis(), topic.getTeachingMaterial().getFile().getUrl())));
			}
			topics.add(newTopic);
		}

		return new ResponseEntity<Iterable<TopicDTO>>(topics, HttpStatus.OK);
	}
	
	
	@PostMapping("/createTopic")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Topic> create(@RequestBody Topic topic){
	    try {
			service.save(topic);
			return new ResponseEntity<Topic>(topic, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Topic>(HttpStatus.BAD_REQUEST);
	}

	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Topic> update(@PathVariable("id") Long id, @RequestBody Topic changedTopic) {
		Topic topicExist = service.findOne(id).orElse(null);
		if (topicExist != null) {
			service.save(changedTopic);
			return new ResponseEntity<Topic>(changedTopic, HttpStatus.OK);
		}
		return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Topic> delete(@PathVariable("id") Long id) {		
		if (service.findOne(id).isPresent()) {
			for(Subject subject : subjectService.findByTopic_id(id)) {
				subject.getTopic().remove(service.findOne(id).get()); // moramo da obrisem topic od subjecta zato sto many to many veza ne dozvoljava regularno brisanje
			}
			service.delete(id);
			return new ResponseEntity<Topic>(HttpStatus.OK);
		}
		return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsByOpisWithTopicType/{objectToChangeID}/{opis}/{topicTypeId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByOpisWithTopicType(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("opis") String opis, @PathVariable("topicTypeId") Long topicTypeId) {
		if (service.existsByOpisWithTopicType(opis, topicTypeId) == true) {
			Topic objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getOpis().equals(opis)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Topic with this description already exist for that type!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Topic with this description already exist for that type!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	 }
	
}
