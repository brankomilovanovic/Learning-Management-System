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

import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TeacherOnRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTeachingDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.SubjectRealization;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;
import rs.ac.singidunum.novisad.isa.service.ProfessorService;
import rs.ac.singidunum.novisad.isa.service.SubjectRealizationService;
import rs.ac.singidunum.novisad.isa.service.TeacherOnRealizationService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subjectRealization")
public class SubjectRealizationController {
	
	@Autowired
	SubjectRealizationService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	TeacherOnRealizationService teacherOnRealizationService;
	
	@GetMapping("/allSubjectRealization")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<SubjectRealizationDTO>> getAll() {
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		
		for (SubjectRealization subjectRealization : service.findAll()) {
			
			/////SUBJECT
			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: subjectRealization.getSubject().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			SubjectDTO subjectDTO = new SubjectDTO(subjectRealization.getSubject().getId(), subjectRealization.getSubject().getNaziv(), subjectRealization.getSubject().getEspb(), 
					subjectRealization.getSubject().isObavezan(),subjectRealization.getSubject().getBrojPredavanja(), subjectRealization.getSubject().getBrojVezbi(), 
						subjectRealization.getSubject().getDrugiObliciNastave(), subjectRealization.getSubject().getIstrazivackiRad(), subjectRealization.getSubject().getOstaliCasovi(), subjectRealization.getSubject().getSilabus(), topicsDTO, null);
			
			///TYPE OF TEACHING
			Set<TypeOfTeachingDTO> typeOfTeachingDTO = new HashSet<>();
			for(TypeOfTeaching typeOfTeaching: subjectRealization.getTeacherOnRealization().getTypeOfTeaching()) { 
				typeOfTeachingDTO.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
			}
			
			////TEACHER ON REALIZATION
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: subjectRealization.getTeacherOnRealization().getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professorDTO = new ProfessorDTO(subjectRealization.getTeacherOnRealization().getProfessor().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getJmbg(), 
					subjectRealization.getTeacherOnRealization().getProfessor().getDateOfBirth(), subjectRealization.getTeacherOnRealization().getProfessor().getAddress(), 
						subjectRealization.getTeacherOnRealization().getProfessor().getPhoneNumber(), subjectRealization.getTeacherOnRealization().getProfessor().getBiography(), 
						new UserDTO(subjectRealization.getTeacherOnRealization().getProfessor().getUser().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getName(), 
							subjectRealization.getTeacherOnRealization().getProfessor().getUser().getSurname(),subjectRealization.getTeacherOnRealization().getProfessor().getUser().getUsername(), 
								subjectRealization.getTeacherOnRealization().getProfessor().getUser().getEmail(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getPassword(), roleDTO));
			
			TeacherOnRealizationDTO teacherOnRealizationDTO = new TeacherOnRealizationDTO(subjectRealization.getTeacherOnRealization().getId(), subjectRealization.getTeacherOnRealization().getBrojCasova(), 
					professorDTO, typeOfTeachingDTO);
			
			///SUBJECT REALIZATION
			SubjectRealizationDTO subjectRealizationDTO = new SubjectRealizationDTO(subjectRealization.getId(), subjectDTO);
			subjectRealizationDTO.setTeacherOnRealization(teacherOnRealizationDTO);
			////CREATE SUBJECT ON REALIZATION
			subjectRealizations.add(subjectRealizationDTO);
		}

		return new ResponseEntity<Iterable<SubjectRealizationDTO>>(subjectRealizations, HttpStatus.OK);
	}
	
	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<Iterable<SubjectRealizationDTO>> findByUsername(@PathVariable("username") String username) {
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		for (SubjectRealization subjectRealization : service.findByUsername(username)) {
			
			/////SUBJECT
			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: subjectRealization.getSubject().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			SubjectDTO subjectDTO = new SubjectDTO(subjectRealization.getSubject().getId(), subjectRealization.getSubject().getNaziv(), subjectRealization.getSubject().getEspb(), 
					subjectRealization.getSubject().isObavezan(),subjectRealization.getSubject().getBrojPredavanja(), subjectRealization.getSubject().getBrojVezbi(), 
						subjectRealization.getSubject().getDrugiObliciNastave(), subjectRealization.getSubject().getIstrazivackiRad(), subjectRealization.getSubject().getOstaliCasovi(), subjectRealization.getSubject().getSilabus(), topicsDTO, null);
			
			///TYPE OF TEACHING
			Set<TypeOfTeachingDTO> typeOfTeachingDTO = new HashSet<>();
			for(TypeOfTeaching typeOfTeaching: subjectRealization.getTeacherOnRealization().getTypeOfTeaching()) { 
				typeOfTeachingDTO.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
			}
			
			////TEACHER ON REALIZATION
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: subjectRealization.getTeacherOnRealization().getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professorDTO = new ProfessorDTO(subjectRealization.getTeacherOnRealization().getProfessor().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getJmbg(), 
					subjectRealization.getTeacherOnRealization().getProfessor().getDateOfBirth(), subjectRealization.getTeacherOnRealization().getProfessor().getAddress(), 
						subjectRealization.getTeacherOnRealization().getProfessor().getPhoneNumber(), subjectRealization.getTeacherOnRealization().getProfessor().getBiography(), 
						new UserDTO(subjectRealization.getTeacherOnRealization().getProfessor().getUser().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getName(), 
							subjectRealization.getTeacherOnRealization().getProfessor().getUser().getSurname(),subjectRealization.getTeacherOnRealization().getProfessor().getUser().getUsername(), 
								subjectRealization.getTeacherOnRealization().getProfessor().getUser().getEmail(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getPassword(), roleDTO));
			
			TeacherOnRealizationDTO teacherOnRealizationDTO = new TeacherOnRealizationDTO(subjectRealization.getTeacherOnRealization().getId(), subjectRealization.getTeacherOnRealization().getBrojCasova(), 
					professorDTO, typeOfTeachingDTO);
			
			///SUBJECT REALIZATION
			SubjectRealizationDTO subjectRealizationDTO = new SubjectRealizationDTO(subjectRealization.getId(), subjectDTO);
			subjectRealizationDTO.setTeacherOnRealization(teacherOnRealizationDTO);
			////CREATE SUBJECT ON REALIZATION
			subjectRealizations.add(subjectRealizationDTO);
		}

		return new ResponseEntity<Iterable<SubjectRealizationDTO>>(subjectRealizations, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<SubjectRealization> create(@RequestBody SubjectRealization subjectRealization){
		try {
			service.save(subjectRealization);
			return new ResponseEntity<SubjectRealization>(subjectRealization, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SubjectRealization>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<SubjectRealization> update(@PathVariable("id") Long id, @RequestBody SubjectRealization changeSubjectRealization) {
		SubjectRealization subjectRealizationExist = service.findOne(id).orElse(null);
		if (subjectRealizationExist != null) {
			service.save(changeSubjectRealization);
			return new ResponseEntity<SubjectRealization>(changeSubjectRealization, HttpStatus.OK);
		}
		return new ResponseEntity<SubjectRealization>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<SubjectRealization> delete(@PathVariable("id") Long id) {		
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<SubjectRealization>(HttpStatus.OK);
		}
		return new ResponseEntity<SubjectRealization>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsSubjectByTypeOfTeaching/{objectToChangeID}/{subjectId}/{typeOfTeachingId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsSubjectByTypeOfTeaching(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("subjectId") Long subjectId, @PathVariable("typeOfTeachingId") Long typeOfTeachingId) {
		if (service.existsSubjectByTypeOfTeaching(subjectId, typeOfTeachingId) == true) {
			SubjectRealization objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getSubject().getId().equals(subjectId)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: There is already that type of teaching in the realization of this subject!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: There is already that type of teaching in the realization of this subject!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
