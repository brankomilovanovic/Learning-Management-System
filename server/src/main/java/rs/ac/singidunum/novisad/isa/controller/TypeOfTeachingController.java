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

import rs.ac.singidunum.novisad.isa.dto.TypeOfTeachingDTO;
import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;
import rs.ac.singidunum.novisad.isa.service.TypeOfTeachingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/typeofteaching")
public class TypeOfTeachingController {
	
	@Autowired
	TypeOfTeachingService service;
	
	@GetMapping("/allTypeOfTeaching")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TypeOfTeachingDTO>> getAllTypeOfTeaching() {
		
		ArrayList<TypeOfTeachingDTO> typeOfTeachings = new ArrayList<TypeOfTeachingDTO>();
		for (TypeOfTeaching typeOfTeaching : service.findAll()) {
			typeOfTeachings.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
		}

		return new ResponseEntity<Iterable<TypeOfTeachingDTO>>(typeOfTeachings, HttpStatus.OK);
	}

}
