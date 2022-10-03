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

import rs.ac.singidunum.novisad.isa.dto.TypeRanksDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.TypeRanks;
import rs.ac.singidunum.novisad.isa.service.TypeRanksService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/typeRanks")
public class TypeRanksController {
	
	@Autowired
	TypeRanksService typeRanksService;
	
	@GetMapping("/allRanks")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<TypeRanksDTO>> getAll() {
		ArrayList<TypeRanksDTO> typeRanks = new ArrayList<TypeRanksDTO>();
		for (TypeRanks typeRank : typeRanksService.findAll()) {
			typeRanks.add(new TypeRanksDTO(typeRank.getId(), typeRank.getName(), typeRank.isActive()));
		}

		return new ResponseEntity<Iterable<TypeRanksDTO>>(typeRanks, HttpStatus.OK);
	}
	
	@PostMapping("/createRanks")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<TypeRanks> create(@RequestBody TypeRanks typeRanks){
	    try {
	    	typeRanks.setName(typeRanks.getName().substring(0,1).toUpperCase() + typeRanks.getName().substring(1).toLowerCase());
			typeRanksService.save(typeRanks);
			return new ResponseEntity<TypeRanks>(typeRanks, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TypeRanks>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<TypeRanks> update(@PathVariable("id") Long typeRanksId, @RequestBody TypeRanks changedTypeRanks) {
		TypeRanks typeRanks = typeRanksService.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			changedTypeRanks.setName(changedTypeRanks.getName().substring(0,1).toUpperCase() + changedTypeRanks.getName().substring(1).toLowerCase());
			typeRanksService.save(changedTypeRanks);
			return new ResponseEntity<TypeRanks>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<TypeRanks>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<TypeRanks> deleteKupovina(@PathVariable("id") Long typeRanksId) {
		if (typeRanksService.findOne(typeRanksId).isPresent()) {
			typeRanksService.delete(typeRanksId);
			return new ResponseEntity<TypeRanks>(HttpStatus.OK);
		}
		return new ResponseEntity<TypeRanks>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existByName/{objectToChangeID}/{name}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existByName(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("name") String name) {
		if (typeRanksService.existByName(name) == true) {
			TypeRanks objectToChange = typeRanksService.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getName().toLowerCase().equals(name.toLowerCase())) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Type rank with that name already exists!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Type rank with that name already exists!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
