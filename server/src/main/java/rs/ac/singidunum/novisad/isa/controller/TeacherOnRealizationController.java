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
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.SubjectRealization;
import rs.ac.singidunum.novisad.isa.model.TeacherOnRealization;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;
import rs.ac.singidunum.novisad.isa.service.TeacherOnRealizationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teacherOnRealizations")
public class TeacherOnRealizationController {
	
	@Autowired
    TeacherOnRealizationService service;
	
	@GetMapping("/allTeacherOnRealizations")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR') or hasRole('STUDENT')")
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> getAll() {
		ArrayList<TeacherOnRealizationDTO> teacherOnRealizations = new ArrayList<TeacherOnRealizationDTO>();
		for (TeacherOnRealization teacherOnRealization : service.findAll()) {
			
			/////TYPE OF TEACHING
			Set<TypeOfTeachingDTO> typeOfTeachingDTO = new HashSet<>();
			for(TypeOfTeaching typeOfTeaching: teacherOnRealization.getTypeOfTeaching()) { 
				typeOfTeachingDTO.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
			}
			
			////SUBJECT REALIZATION
			Set<SubjectRealizationDTO> subjectRealizationDTO = new HashSet<>();
			for(SubjectRealization subjectRealization: teacherOnRealization.getSubjectRealization()) {
				/////TOPIC FOR SUBJECT
				Set<TopicDTO> topicsDTO = new HashSet<>();
				for(Topic topic: subjectRealization.getSubject().getTopic()) { 
					topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
				}
				
				SubjectDTO subjectDTO = new SubjectDTO(subjectRealization.getSubject().getId(), subjectRealization.getSubject().getNaziv(), subjectRealization.getSubject().getEspb(), 
						subjectRealization.getSubject().isObavezan(),subjectRealization.getSubject().getBrojPredavanja(), subjectRealization.getSubject().getBrojVezbi(), 
							subjectRealization.getSubject().getDrugiObliciNastave(), subjectRealization.getSubject().getIstrazivackiRad(), subjectRealization.getSubject().getOstaliCasovi(), subjectRealization.getSubject().getSilabus(), topicsDTO, null);
				subjectRealizationDTO.add(new SubjectRealizationDTO(subjectRealization.getId(), subjectDTO));
			}
			
			/////TEACHER ON REALIZATION
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: teacherOnRealization.getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professorDTO = new ProfessorDTO(teacherOnRealization.getProfessor().getId(), teacherOnRealization.getProfessor().getJmbg(), teacherOnRealization.getProfessor().getDateOfBirth(), 
					teacherOnRealization.getProfessor().getAddress(), teacherOnRealization.getProfessor().getPhoneNumber(), teacherOnRealization.getProfessor().getBiography(), 
					new UserDTO(teacherOnRealization.getProfessor().getUser().getId(), teacherOnRealization.getProfessor().getUser().getName(), teacherOnRealization.getProfessor().getUser().getSurname(),
							teacherOnRealization.getProfessor().getUser().getUsername(), teacherOnRealization.getProfessor().getUser().getEmail(), teacherOnRealization.getProfessor().getUser().getPassword(), roleDTO));
			
			TeacherOnRealizationDTO teacherOnRealizationDTO = new TeacherOnRealizationDTO(teacherOnRealization.getId(), teacherOnRealization.getBrojCasova(), professorDTO, typeOfTeachingDTO);
			teacherOnRealizationDTO.setSubjectRealization(subjectRealizationDTO);
					
			teacherOnRealizations.add(teacherOnRealizationDTO);
		}

		return new ResponseEntity<Iterable<TeacherOnRealizationDTO>>(teacherOnRealizations, HttpStatus.OK);
	}
	
	@GetMapping("/getByProfessorId/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR') or hasRole('STUDENT')")
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> findByProfessorId(@PathVariable("id") Long id) {
		ArrayList<TeacherOnRealizationDTO> teacherOnRealizations = new ArrayList<TeacherOnRealizationDTO>();
		for (TeacherOnRealization teacherOnRealization : service.findByProfessorId(id)) {
		/////TYPE OF TEACHING
			Set<TypeOfTeachingDTO> typeOfTeachingDTO = new HashSet<>();
			for(TypeOfTeaching typeOfTeaching: teacherOnRealization.getTypeOfTeaching()) { 
				typeOfTeachingDTO.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
			}
					
			////SUBJECT REALIZATION
			Set<SubjectRealizationDTO> subjectRealizationDTO = new HashSet<>();
			for(SubjectRealization subjectRealization: teacherOnRealization.getSubjectRealization()) {
				/////TOPIC FOR SUBJECT
				Set<TopicDTO> topicsDTO = new HashSet<>();
				for(Topic topic: subjectRealization.getSubject().getTopic()) { 
					topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
				}
						
				SubjectDTO subjectDTO = new SubjectDTO(subjectRealization.getSubject().getId(), subjectRealization.getSubject().getNaziv(), subjectRealization.getSubject().getEspb(), 
						subjectRealization.getSubject().isObavezan(),subjectRealization.getSubject().getBrojPredavanja(), subjectRealization.getSubject().getBrojVezbi(), 
							subjectRealization.getSubject().getDrugiObliciNastave(), subjectRealization.getSubject().getIstrazivackiRad(), subjectRealization.getSubject().getOstaliCasovi(), subjectRealization.getSubject().getSilabus(), topicsDTO, null);
				subjectRealizationDTO.add(new SubjectRealizationDTO(subjectRealization.getId(), subjectDTO));
			}
					
			/////TEACHER ON REALIZATION
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: teacherOnRealization.getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
					
			ProfessorDTO professorDTO = new ProfessorDTO(teacherOnRealization.getProfessor().getId(), teacherOnRealization.getProfessor().getJmbg(), teacherOnRealization.getProfessor().getDateOfBirth(), 
					teacherOnRealization.getProfessor().getAddress(), teacherOnRealization.getProfessor().getPhoneNumber(), teacherOnRealization.getProfessor().getBiography(), 
						new UserDTO(teacherOnRealization.getProfessor().getUser().getId(), teacherOnRealization.getProfessor().getUser().getName(), teacherOnRealization.getProfessor().getUser().getSurname(),
							teacherOnRealization.getProfessor().getUser().getUsername(), teacherOnRealization.getProfessor().getUser().getEmail(), teacherOnRealization.getProfessor().getUser().getPassword(), roleDTO));
					
			TeacherOnRealizationDTO teacherOnRealizationDTO = new TeacherOnRealizationDTO(teacherOnRealization.getId(), teacherOnRealization.getBrojCasova(), professorDTO, typeOfTeachingDTO);
			teacherOnRealizationDTO.setSubjectRealization(subjectRealizationDTO);
							
			teacherOnRealizations.add(teacherOnRealizationDTO);
		}
		return new ResponseEntity<Iterable<TeacherOnRealizationDTO>>(teacherOnRealizations, HttpStatus.OK);
	}

	
	@PostMapping("/createTeacherOnRealizations")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TeacherOnRealization> create(@RequestBody TeacherOnRealization teacherOnRealization){
	    try {
			service.save(teacherOnRealization);
			return new ResponseEntity<TeacherOnRealization>(teacherOnRealization, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TeacherOnRealization>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TeacherOnRealization> update(@PathVariable("id") Long id, @RequestBody TeacherOnRealization changeTeacherOnRealization) {
		TeacherOnRealization teacherOnRealizations = service.findOne(id).orElse(null);
		if (teacherOnRealizations != null) {
			service.save(changeTeacherOnRealization);
			return new ResponseEntity<TeacherOnRealization>(changeTeacherOnRealization, HttpStatus.OK);
		}
		return new ResponseEntity<TeacherOnRealization>(HttpStatus.NOT_FOUND);
	}

	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TeacherOnRealization> delete(@PathVariable("id") Long id) {		
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<TeacherOnRealization>(HttpStatus.OK);
		}
		return new ResponseEntity<TeacherOnRealization>(HttpStatus.NOT_FOUND);
	}
}
