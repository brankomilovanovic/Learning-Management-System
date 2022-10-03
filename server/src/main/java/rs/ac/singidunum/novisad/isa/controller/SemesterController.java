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

import rs.ac.singidunum.novisad.isa.dto.SemesterDTO;
import rs.ac.singidunum.novisad.isa.model.Semester;
import rs.ac.singidunum.novisad.isa.service.SemesterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/semester")
public class SemesterController {
	
	@Autowired
	private SemesterService s;
	
	@GetMapping("/allsemester")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<SemesterDTO>> getAll() {
		ArrayList<SemesterDTO> typeRanks = new ArrayList<SemesterDTO>();
		for (Semester ss : s.findAll()) {
			typeRanks.add(new SemesterDTO(ss.getId(),ss.getSemester(),null));
		}

		return new ResponseEntity<Iterable<SemesterDTO>>(typeRanks, HttpStatus.OK);
	}
	
	@PostMapping("/createRanks")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Semester> create(@RequestBody Semester typeRanks){
	    try {
			s.save(typeRanks);
			return new ResponseEntity<Semester>(typeRanks, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Semester>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Semester> update(@PathVariable("id") Long typeRanksId, @RequestBody Semester changedTypeRanks) {
		Semester typeRanks = s.findOne(typeRanksId).orElse(null);
		if (typeRanks != null) {
			s.save(changedTypeRanks);
			return new ResponseEntity<Semester>(changedTypeRanks, HttpStatus.OK);
		}
		return new ResponseEntity<Semester>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Semester> deleteKupovina(@PathVariable("id") Long typeRanksId) {
		if (s.findOne(typeRanksId).isPresent()) {
			s.delete(typeRanksId);
			return new ResponseEntity<Semester>(HttpStatus.OK);
		}
		return new ResponseEntity<Semester>(HttpStatus.NOT_FOUND);
	}
}
