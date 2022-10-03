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

import rs.ac.singidunum.novisad.isa.dto.ScientificAreaDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.ScientificArea;
import rs.ac.singidunum.novisad.isa.service.ScientificAreaService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/scientificAreas")
public class ScientificAreaController {
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	@GetMapping("/allScientificAreas")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<ScientificAreaDTO>> getAll() {
		ArrayList<ScientificAreaDTO> cientificAreas = new ArrayList<ScientificAreaDTO>();
		for (ScientificArea cientificArea : scientificAreaService.findAll()) {
			cientificAreas.add(new ScientificAreaDTO(cientificArea.getId(), cientificArea.getName(), cientificArea.isActive()));
		}

		return new ResponseEntity<Iterable<ScientificAreaDTO>>(cientificAreas, HttpStatus.OK);
	}
	
	@PostMapping("/createScientificAreas")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<ScientificArea> create(@RequestBody ScientificArea cientificArea){
	    try {
			scientificAreaService.save(cientificArea);
			return new ResponseEntity<ScientificArea>(cientificArea, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ScientificArea>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<ScientificArea> update(@PathVariable("id") Long scientificAreaId, @RequestBody ScientificArea changedScientificArea) {
		ScientificArea typeRanks = scientificAreaService.findOne(scientificAreaId).orElse(null);
		if (typeRanks != null) {
			changedScientificArea.setId(scientificAreaId);
			changedScientificArea = scientificAreaService.save(changedScientificArea);
			return new ResponseEntity<ScientificArea>(changedScientificArea, HttpStatus.OK);
		}
		return new ResponseEntity<ScientificArea>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<ScientificArea> deleteKupovina(@PathVariable("id") Long scientificAreaId) {
		if (scientificAreaService.findOne(scientificAreaId).isPresent()) {
			scientificAreaService.delete(scientificAreaId);
			return new ResponseEntity<ScientificArea>(HttpStatus.OK);
		}
		return new ResponseEntity<ScientificArea>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existScientificArea/{objectToChangeID}/{name}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existScientificArea(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("name") String name) {
		if (scientificAreaService.existScientificArea(name) == true) {
			ScientificArea objectToChange = scientificAreaService.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getName().equals(name)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Scientific area with this name already exist!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Scientific area with this name already exist!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Scientific area with this name don't exist!"));
	}
}
