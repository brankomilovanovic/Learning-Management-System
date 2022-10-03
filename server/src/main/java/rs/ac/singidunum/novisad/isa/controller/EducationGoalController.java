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

import rs.ac.singidunum.novisad.isa.dto.EducationGoalDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.EducationGoal;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.service.EducationGoalService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/educationGoals")
public class EducationGoalController {

	@Autowired
	private EducationGoalService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<EducationGoalDTO>> getAll() {
		ArrayList<EducationGoalDTO> educationGoalDTO = new ArrayList<EducationGoalDTO>();
		for(EducationGoal educationGoal : service.findAll()) {
			educationGoalDTO.add(new EducationGoalDTO(educationGoal.getId(), educationGoal.getOpis(), 
					new TopicDTO(educationGoal.getTopic().getId(), educationGoal.getTopic().getOpis(), 
							new TypeOfTopicDTO(educationGoal.getTopic().getTopicType().getId(), educationGoal.getTopic().getTopicType().getNaziv()))));
		}
		return new ResponseEntity<Iterable<EducationGoalDTO>>(educationGoalDTO, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EducationGoal> create(@RequestBody EducationGoal object){
	    try {
	    	service.save(object);
			return new ResponseEntity<EducationGoal>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<EducationGoal>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EducationGoal> update(@PathVariable("id") Long id, @RequestBody EducationGoal changedObject) {
		EducationGoal objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<EducationGoal>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<EducationGoal>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EducationGoal> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<EducationGoal>(HttpStatus.OK);
		}
		return new ResponseEntity<EducationGoal>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsByTopicID/{objectToChangeID}/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByTopicID(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("id") Long id) {
		if (service.existsByTopicID(id) == true) {
			EducationGoal objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getTopic().getId().equals(id)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Education goal already exists with this topic!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Education goal already exists with this topic!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Education goal don't exists with this topic!"));
	}
	
	@GetMapping("/existsByOpis/{objectToChangeID}/{opis}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByOpis(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("opis") String opis) {
		if (service.existsByOpis(opis) == true) {
			EducationGoal objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getOpis().equals(opis)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Education goal already exists with this description!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Education goal already exists with this description!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Education goal don't exists with this description!"));
	}
}
