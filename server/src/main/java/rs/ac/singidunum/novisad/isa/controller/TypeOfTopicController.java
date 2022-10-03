package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.model.TypeOfTopic;
import rs.ac.singidunum.novisad.isa.service.TypeOfTopicService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/typeoftopic")
public class TypeOfTopicController {

	@Autowired
	TypeOfTopicService service;
	
	@GetMapping("/allTypeOfTopic")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TypeOfTopicDTO>> getAll() {
		
		ArrayList<TypeOfTopicDTO> typeOfTopics = new ArrayList<TypeOfTopicDTO>();
		for (TypeOfTopic typeOfTopic : service.findAll()) {
			typeOfTopics.add(new TypeOfTopicDTO(typeOfTopic.getId(), typeOfTopic.getNaziv()));
		}

		return new ResponseEntity<Iterable<TypeOfTopicDTO>>(typeOfTopics, HttpStatus.OK);
	}

}
