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

import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectNotificationsDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;
import rs.ac.singidunum.novisad.isa.service.SubjectNotificationsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/subject_notifications")
public class SubjectNotificationsController {

	@Autowired
	private SubjectNotificationsService service;
	
	@GetMapping
	public ResponseEntity<Iterable<SubjectNotificationsDTO>> getAll() {
		ArrayList<SubjectNotificationsDTO> objects = new ArrayList<SubjectNotificationsDTO>();
		for(SubjectNotifications object : service.findAll()) {
			objects.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), 
					new SubjectDTO(object.getSubject().getId(), null, 0, false, 0, 0, 0, 0, 0, null, null, null),
					new ProfessorDTO(object.getProfessor().getId(), null, null, null, null, null,
						new UserDTO(object.getProfessor().getUser().getId(), object.getProfessor().getUser().getName(), object.getProfessor().getUser().getSurname(), null, null, null, null))));
		}
		return new ResponseEntity<Iterable<SubjectNotificationsDTO>>(objects, HttpStatus.OK);
	}
	
	@GetMapping("/{subjectId}")
	public ResponseEntity<Iterable<SubjectNotificationsDTO>> getBySubjectId(@PathVariable("subjectId") Long subjectId) {
		ArrayList<SubjectNotificationsDTO> objects = new ArrayList<SubjectNotificationsDTO>();
		for(SubjectNotifications object : service.findBySubjectId(subjectId)) {
			objects.add(new SubjectNotificationsDTO(object.getId(), object.getNaziv(), object.getOpis(), 
					new SubjectDTO(object.getSubject().getId(), object.getSubject().getNaziv(), 0, false, 0, 0, 0, 0, 0, null, null, null),
					new ProfessorDTO(object.getProfessor().getId(), null, null, null, null, null,
						new UserDTO(object.getProfessor().getUser().getId(), object.getProfessor().getUser().getName(), object.getProfessor().getUser().getSurname(), null, null, null, null))));
		}
		return new ResponseEntity<Iterable<SubjectNotificationsDTO>>(objects, HttpStatus.OK);
	}

	
	@PostMapping
	public ResponseEntity<SubjectNotifications> create(@RequestBody SubjectNotifications object){
	    try {
	    	service.save(object);
			return new ResponseEntity<SubjectNotifications>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<SubjectNotifications>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<SubjectNotifications> update(@PathVariable("id") Long id, @RequestBody SubjectNotifications changedObject) {
		SubjectNotifications objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<SubjectNotifications>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<SubjectNotifications>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<SubjectNotifications> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<SubjectNotifications>(HttpStatus.OK);
		}
		return new ResponseEntity<SubjectNotifications>(HttpStatus.NOT_FOUND);
	}
}
