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

import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectNotificationsDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.service.SubjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/subjects")
public class SubjectsController {
	
	@Autowired
	private SubjectService ss;
	
	@GetMapping("/allSubjects")
//	@PreAuthorize("hasRole('ADMINISTRATOR') OR hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<SubjectDTO>> getAll() {
		ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		for(Subject subject : ss.findAll()) {
			
			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: subject.getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			
			Set<SubjectNotificationsDTO> subjectNotificationsDTO = new HashSet<>();
			for(SubjectNotifications object: subject.getSubjectNotifications()) { 
				subjectNotificationsDTO.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), null, null));
			}
			
			SubjectDTO newSubject = new SubjectDTO(subject.getId(), subject.getNaziv(), subject.getEspb(), subject.isObavezan(), subject.getBrojPredavanja(), subject.getBrojVezbi(),
					subject.getDrugiObliciNastave(), subject.getIstrazivackiRad(), subject.getOstaliCasovi(), subject.getSilabus(), topicsDTO, null);
			newSubject.setSubjectNotifications(subjectNotificationsDTO);
			
			subjects.add(newSubject);
		}
		return new ResponseEntity<Iterable<SubjectDTO>>(subjects, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubjectDTO> getone(@PathVariable("id") Long id) {
		Optional<Subject> subject = ss.findOne(id);
		SubjectDTO subjectDTO = new SubjectDTO();
		if (subject.isPresent()) {
			
			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: subject.get().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			
			Set<SubjectNotificationsDTO> subjectNotificationsDTO = new HashSet<>();
			for(SubjectNotifications object: subject.get().getSubjectNotifications()) { 
				subjectNotificationsDTO.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), null, null));
			}
			
			subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getNaziv(), subject.get().getEspb(), subject.get().isObavezan(), subject.get().getBrojPredavanja(), subject.get().getBrojVezbi(),
					subject.get().getDrugiObliciNastave(), subject.get().getIstrazivackiRad(), subject.get().getOstaliCasovi(), subject.get().getSilabus(), topicsDTO, null);
			subjectDTO.setSubjectNotifications(subjectNotificationsDTO);

			return new ResponseEntity<SubjectDTO>(subjectDTO, HttpStatus.OK);
		} 
		return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/createSubject")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Subject> create(@RequestBody Subject subject){
	    try {
	    	ss.save(subject);
			return new ResponseEntity<Subject>(subject, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<Subject>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') OR hasRole('PROFESSOR')")
	public ResponseEntity<Subject> update(@PathVariable("id") Long typeRanksId, @RequestBody Subject changedTypeRanks) {
		Subject typeRanks = ss.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			ss.save(changedTypeRanks);
			return new ResponseEntity<Subject>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Subject> delete(@PathVariable("id") Long typeRanksId) {
		if (ss.findOne(typeRanksId).isPresent()) {
			ss.delete(typeRanksId);
			return new ResponseEntity<Subject>(HttpStatus.OK);
		}
		return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/checkNaziv/{userId}/{naziv}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> checkNaziv(@PathVariable("userId") String userId, @PathVariable("naziv") String naziv) {
		if (ss.existsByNaziv(naziv) == true) {
			if(!userId.equals("null")) {
				Optional<Subject> subject = ss.findOne(Long.parseLong(userId));
				if(!naziv.equals(subject.get().getNaziv())) { return ResponseEntity.badRequest().body(new MessageResponse("Error: Name is already taken!")); }
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Name is already taken!"));
			}
		}
		return ResponseEntity.ok(new MessageResponse("Name is free!"));
	}
}
