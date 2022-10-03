package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.ScientificArea;
import rs.ac.singidunum.novisad.isa.repository.ScientificAreaRepository;

@Service
public class ScientificAreaService {
	
	@Autowired
	private ScientificAreaRepository scientificAreaRepository;
	
	public Iterable<ScientificArea> findAll() {
		return scientificAreaRepository.findAll();
	}
	
	public Optional<ScientificArea> findOne(Long id) {
		return scientificAreaRepository.findById(id);
	}
	
	public ScientificArea save(ScientificArea scientificArea) {
		return scientificAreaRepository.save(scientificArea);
	}
	
	public boolean existScientificArea(String name) {
		return scientificAreaRepository.existScientificArea(name);
	}
	
	public void delete(Long id) {
		scientificAreaRepository.deleteById(id);
	}
	
	public void delete(ScientificArea scientificArea) {
		scientificAreaRepository.delete(scientificArea);
	}
}
