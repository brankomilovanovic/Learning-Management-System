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

import rs.ac.singidunum.novisad.isa.dto.TypeEvaluationDTO;
import rs.ac.singidunum.novisad.isa.model.TypeEvaluation;
import rs.ac.singidunum.novisad.isa.service.TypeEvaluationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/typeEvaluations")
public class TypeEvaluationController {

	@Autowired
	private TypeEvaluationService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<TypeEvaluationDTO>> getAll() {
		ArrayList<TypeEvaluationDTO> typeEvaluations = new ArrayList<TypeEvaluationDTO>();
		for(TypeEvaluation typeEvaluation : service.findAll()) {
			typeEvaluations.add(new TypeEvaluationDTO(typeEvaluation.getId(), typeEvaluation.getTipEvaluacije()));
		}
		return new ResponseEntity<Iterable<TypeEvaluationDTO>>(typeEvaluations, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<TypeEvaluation> create(@RequestBody TypeEvaluation object){
	    try {
	    	service.save(object);
			return new ResponseEntity<TypeEvaluation>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<TypeEvaluation>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<TypeEvaluation> update(@PathVariable("id") Long id, @RequestBody TypeEvaluation changedObject) {
		TypeEvaluation objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<TypeEvaluation>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<TypeEvaluation>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<TypeEvaluation> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<TypeEvaluation>(HttpStatus.OK);
		}
		return new ResponseEntity<TypeEvaluation>(HttpStatus.NOT_FOUND);
	}
}
