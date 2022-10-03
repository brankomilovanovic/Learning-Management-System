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

import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.TeachingMaterialDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.TeachingMaterial;
import rs.ac.singidunum.novisad.isa.service.TeachingMaterialService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/teaching_material")
public class TeachingMaterialController {

	@Autowired
	private TeachingMaterialService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<TeachingMaterialDTO>> getAll() {
		ArrayList<TeachingMaterialDTO> teachingMaterials = new ArrayList<TeachingMaterialDTO>();
		for(TeachingMaterial teachingMaterial : service.findAll()) {
			
			teachingMaterials.add(new TeachingMaterialDTO(teachingMaterial.getId(), teachingMaterial.getNaziv(), teachingMaterial.getGodinaIzdavanja(), teachingMaterial.getAutori(), 
					new FileDTO(teachingMaterial.getFile().getId(), teachingMaterial.getFile().getOpis(), teachingMaterial.getFile().getUrl())));
		}
		return new ResponseEntity<Iterable<TeachingMaterialDTO>>(teachingMaterials, HttpStatus.OK);
	}
	
	@GetMapping("/getAllAuthors")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<String>> getAllAuthors() {
		ArrayList<String> authors = new ArrayList<String>();
		for(String autor : service.getAllAuthors()) {
			authors.add(autor);
		}
		return new ResponseEntity<Iterable<String>>(authors, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<TeachingMaterial> create(@RequestBody TeachingMaterial teachingMaterial){
	    try {
	    	service.save(teachingMaterial);
			return new ResponseEntity<TeachingMaterial>(teachingMaterial, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<TeachingMaterial>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<TeachingMaterial> update(@PathVariable("id") Long typeRanksId, @RequestBody TeachingMaterial changedTypeRanks) {
		TeachingMaterial typeRanks = service.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			service.save(changedTypeRanks);
			return new ResponseEntity<TeachingMaterial>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<TeachingMaterial>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<TeachingMaterial> delete(@PathVariable("id") Long typeRanksId) {
		if (service.findOne(typeRanksId).isPresent()) {
			service.delete(typeRanksId);
			return new ResponseEntity<TeachingMaterial>(HttpStatus.OK);
		}
		return new ResponseEntity<TeachingMaterial>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsByNaziv/{objectToChangeID}/{naziv}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<?> existsByNaziv(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("naziv") String naziv) {
		if (service.existsByNaziv(naziv) == true) {
			TeachingMaterial objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getNaziv().equals(naziv)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Teaching material already exists with this name!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Teaching material already exists with this name!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
