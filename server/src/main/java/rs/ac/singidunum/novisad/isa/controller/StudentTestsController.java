package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.Optional;

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

import rs.ac.singidunum.novisad.isa.dto.StudentOnTheYearDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentTestsDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.model.StudentTests;
import rs.ac.singidunum.novisad.isa.service.StudentTestsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/student_tests")
public class StudentTestsController {

	@Autowired
	private StudentTestsService service;
	
	@GetMapping("/alltests")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<StudentTestsDTO>> getAll() {
		ArrayList<StudentTestsDTO> studentTests = new ArrayList<StudentTestsDTO>();
		for(StudentTests studentTest : service.findAll()) {
			studentTests.add(new StudentTestsDTO(studentTest.getId(), studentTest.getKolokvijum1(), studentTest.getKolokvijum2(), studentTest.getIspit(), studentTest.getAktivnost(),
					studentTest.getOcena(), new StudentOnTheYearDTO(studentTest.getStudentOnTheYear().getId(), null, null, null, null, null), 
					new SubjectDTO(studentTest.getSubject().getId(), null, 0, false, 0, 0, 0, 0, 0, null, null, null)));
		}
		return new ResponseEntity<Iterable<StudentTestsDTO>>(studentTests, HttpStatus.OK);
	}
	
	@GetMapping("/{studentOnTheYearID}/{subjectID}")
	public ResponseEntity<StudentTestsDTO> getByStudentOnTheYearAndSubject(@PathVariable("studentOnTheYearID") Long studentOnTheYearID, @PathVariable("subjectID") Long subjectID) {
		Optional<StudentTests> studentTest = service.findByStudentOnTheYearAndSubject(studentOnTheYearID, subjectID);
		StudentTestsDTO studentTestsDTO = new StudentTestsDTO();
		if (studentTest.isPresent()) {
			studentTestsDTO = new StudentTestsDTO(studentTest.get().getId(), studentTest.get().getKolokvijum1(), studentTest.get().getKolokvijum2(), 
						studentTest.get().getIspit(), studentTest.get().getAktivnost(),
					studentTest.get().getOcena(), new StudentOnTheYearDTO(studentTest.get().getStudentOnTheYear().getId(), null, null, null, null, null), 
					new SubjectDTO(studentTest.get().getSubject().getId(), null, 0, false, 0, 0, 0, 0, 0, null, null, null));
			return new ResponseEntity<StudentTestsDTO>(studentTestsDTO, HttpStatus.OK);
		} 
		return new ResponseEntity<StudentTestsDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentTests> create(@RequestBody StudentTests object){
	    try {
	    	service.save(object);
			return new ResponseEntity<StudentTests>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<StudentTests>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentTests> update(@PathVariable("id") Long id, @RequestBody StudentTests changedObject) {
		StudentTests objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<StudentTests>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<StudentTests>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<StudentTests> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<StudentTests>(HttpStatus.OK);
		}
		return new ResponseEntity<StudentTests>(HttpStatus.NOT_FOUND);
	}
}
