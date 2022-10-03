package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Faculty;
import rs.ac.singidunum.novisad.isa.repository.FacultyRepository;


@Service
public class FacultyService {

	@Autowired
	private FacultyRepository repository;
	

	public Iterable<Faculty> findAll() {
		return repository.findAll();
	}
	
	public Optional<Faculty> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Iterable<Faculty> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public boolean existsFacultyNameInUniversity(String nameFaculty, Long idUniversity) {
		return repository.existsFacultyNameInUniversity(nameFaculty, idUniversity);
	}
	
	public Faculty save(Faculty faculty) {
		return repository.save(faculty);
	}
	
	public void delete(Faculty faculty) {
		repository.delete(faculty);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
