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

import rs.ac.singidunum.novisad.isa.dto.EvaluationInstrumentDTO;
import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.model.EvaluationInstrument;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.service.EvaluationInstrumentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/evaluationInstruments")
public class EvaluationInstrumentController {
	
	@Autowired
	private EvaluationInstrumentService service;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Iterable<EvaluationInstrumentDTO>> getAll() {
		ArrayList<EvaluationInstrumentDTO> evaluationInstruments = new ArrayList<EvaluationInstrumentDTO>();
		for(EvaluationInstrument evaluationInstrument : service.findAll()) {
			evaluationInstruments.add(new EvaluationInstrumentDTO(evaluationInstrument.getId(), evaluationInstrument.getTipTestiranja(), 
					new FileDTO(evaluationInstrument.getFile().getId(), evaluationInstrument.getFile().getOpis(), evaluationInstrument.getFile().getUrl())));
		}
		return new ResponseEntity<Iterable<EvaluationInstrumentDTO>>(evaluationInstruments, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EvaluationInstrument> create(@RequestBody EvaluationInstrument object){
	    try {
	    	service.save(object);
			return new ResponseEntity<EvaluationInstrument>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<EvaluationInstrument>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<EvaluationInstrument> update(@PathVariable("id") Long id, @RequestBody EvaluationInstrument changedObject) {
		EvaluationInstrument objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<EvaluationInstrument>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<EvaluationInstrument>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<EvaluationInstrument> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<EvaluationInstrument>(HttpStatus.OK);
		}
		return new ResponseEntity<EvaluationInstrument>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsByFileID/{objectToChangeID}/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByFileID(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("id") Long id) {
		if (service.existsByFileID(id) == true) {
			EvaluationInstrument objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getFile().getId().equals(id)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Evaluation instrunet already exists with this file!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Evaluation instrunet already exists with this file!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Evaluation instrument don't exists with this file!"));
	}
	
	@GetMapping("/existsByTipTestiranja/{objectToChangeID}/{tipTestiranja}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByTipTestiranja(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("tipTestiranja") String tipTestiranja) {
		if (service.existsByTipTestiranja(tipTestiranja) == true) {
			EvaluationInstrument objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getTipTestiranja().equals(tipTestiranja)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Evaluation instrunet already exists with this type of testing!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Evaluation instrunet already exists with this type of testing!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Evaluation instrunet don't exists with this type of testing!"));
	}
}
