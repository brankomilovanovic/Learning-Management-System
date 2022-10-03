package rs.ac.singidunum.novisad.isa.controller;

import java.math.BigInteger;
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

import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.FollowSubDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.FollowSub;
import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.dto.SubjectNotificationsDTO;
import rs.ac.singidunum.novisad.isa.dto.TeachingMaterialDTO;
import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;
import rs.ac.singidunum.novisad.isa.service.FollowSubService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/followsub")
public class FollowSubController {
	
	@Autowired
	FollowSubService f;
	
	@GetMapping("/allfollow")
	public ResponseEntity<Iterable<FollowSubDTO>> getAll(){
		ArrayList<FollowSubDTO> follow = new ArrayList<FollowSubDTO>();
		UserDTO userDTO = new UserDTO();
		for(FollowSub f : f.findAll()) {
			Set<TopicDTO> topicsDTO = new HashSet<>();
			
			
			userDTO = new UserDTO(f.getUser().getId(), null, null, null, null, null, null);
			
			Set<SubjectDTO> subjectDTO = new HashSet<>();
			for(Subject subject: f.getSubjects()) {
				subjectDTO.add(new SubjectDTO(subject.getId(),subject.getNaziv(),subject.getEspb(),
						subject.isObavezan(),subject.getBrojPredavanja(),subject.getBrojVezbi(),subject.getDrugiObliciNastave()
						,subject.getIstrazivackiRad(),subject.getOstaliCasovi(),subject.getSilabus(), topicsDTO, null));
				
				for(Topic topic: subject.getTopic()) { 
					topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
				}
			}
			
			follow.add(new FollowSubDTO(f.getId(),userDTO,subjectDTO));
		}
		
		return new ResponseEntity<Iterable<FollowSubDTO>>(follow, HttpStatus.OK);
	}
	
	@GetMapping("/subjects/{id}")
	public ResponseEntity<Iterable<FollowSubDTO>> findById(@PathVariable("id") Long id) {
		ArrayList<FollowSubDTO> gosti = new ArrayList<FollowSubDTO>();
			for(FollowSub fl : f.findByUserId(id)) {
				
				Set<TopicDTO> topics = new HashSet<>();
				Set<SubjectDTO> subjectDTO = new HashSet<>();
			
				for(Subject subject: fl.getSubjects()) {
					
					Set<SubjectNotificationsDTO> subjectNotifications = new HashSet<SubjectNotificationsDTO>();
					for(SubjectNotifications object : subject.getSubjectNotifications()) {
						subjectNotifications.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), 
								new SubjectDTO(object.getSubject().getId(), object.getSubject().getNaziv(), 0, false, 0, 0, 0, 0, 0, null, null, null), null));
					}

					for(Topic topic: subject.getTopic()) { 
						TopicDTO topicDTO = new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv()));
						topicDTO.setTeachingMaterial(new TeachingMaterialDTO(topic.getTeachingMaterial().getId(), topic.getTeachingMaterial().getNaziv(), 
								topic.getTeachingMaterial().getGodinaIzdavanja(), topic.getTeachingMaterial().getAutori(), 
								new FileDTO(topic.getTeachingMaterial().getFile().getId(), topic.getTeachingMaterial().getFile().getOpis(), topic.getTeachingMaterial().getFile().getUrl())));
						topics.add(topicDTO);
					}
					
					SubjectDTO subjectNew = new SubjectDTO();
					subjectNew = new SubjectDTO(subject.getId(),subject.getNaziv(),subject.getEspb(),
							subject.isObavezan(),subject.getBrojPredavanja(),subject.getBrojVezbi(),subject.getDrugiObliciNastave()
							,subject.getIstrazivackiRad(),subject.getOstaliCasovi(),subject.getSilabus(), topics, null);
					subjectNew.setSubjectNotifications(subjectNotifications);
					subjectDTO.add(subjectNew);
					
				}
				gosti.add(new FollowSubDTO(null, null, subjectDTO));
			}
		return new ResponseEntity<Iterable<FollowSubDTO>>(gosti, HttpStatus.OK);
	}
	
	@GetMapping("/findUserForSubject/{subject_id}")
	public ResponseEntity<Iterable<BigInteger>> getAllUser(@PathVariable("subject_id") Long subject_id){
		ArrayList<BigInteger> users = new ArrayList<BigInteger>();
		for(BigInteger userID : f.findStudentOnSubject(subject_id)) {
			users.add(userID);
		}
		return new ResponseEntity<Iterable<BigInteger>>(users, HttpStatus.OK);
	}

	
	@PostMapping("/createfollow")
	public ResponseEntity<FollowSub> create(@RequestBody FollowSub yearOfStudy){
	    try {
	    	f.save(yearOfStudy);
			return new ResponseEntity<FollowSub>(yearOfStudy, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FollowSub>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<FollowSub> update(@PathVariable("id") Long yearid, @RequestBody FollowSub changedYearOfStudy) {
		FollowSub yae = f.findOne(yearid).orElse(null);
		if (yae != null) {
			f.save(changedYearOfStudy);
			return new ResponseEntity<FollowSub>(changedYearOfStudy, HttpStatus.OK);
		}
		return new ResponseEntity<FollowSub>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<FollowSub> deleteKupovina(@PathVariable("id") Long YearOfStudyid) {
		if (f.findOne(YearOfStudyid).isPresent()) {
			f.delete(YearOfStudyid);
			return new ResponseEntity<FollowSub>(HttpStatus.OK);
		}
		return new ResponseEntity<FollowSub>(HttpStatus.NOT_FOUND);
	}
	
}

