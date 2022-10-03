package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TeachingMaterial;
import rs.ac.singidunum.novisad.isa.repository.TeachingMaterialRepository;

@Service
public class TeachingMaterialService {

	@Autowired
	private TeachingMaterialRepository repository;

	public Iterable<TeachingMaterial> findAll() {
		return repository.findAll();
	}
	
	public Iterable<String> getAllAuthors() {
		return repository.getAllAuthors();
	}

	public Optional<TeachingMaterial> findOne(Long id) {
		return repository.findById(id);
	}
	
	public boolean existsByNaziv(String naziv) {
		return repository.existsByNaziv(naziv);
	}
	
	public TeachingMaterial save(TeachingMaterial object) {
		return repository.save(object);
	}
	
	public void delete(TeachingMaterial object) {
		repository.delete(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
