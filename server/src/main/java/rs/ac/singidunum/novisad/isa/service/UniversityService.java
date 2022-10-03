package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.University;
import rs.ac.singidunum.novisad.isa.repository.UniversityRepository;

@Service
public class UniversityService {

	@Autowired
	private UniversityRepository repository;
	
	public Iterable<University> findAll() {
		return repository.findAll();
	}
	
	public Iterable<University> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public Optional<University> findOne(Long id) {
		return repository.findById(id);
	}
	
	public boolean existByName(String name) {
		return repository.existByName(name);
	}
	
	public University save(University university) {
		return repository.save(university);
	}
	
	public void delete(University university) {
		repository.delete(university);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
