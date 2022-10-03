package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.EducationGoal;
import rs.ac.singidunum.novisad.isa.repository.EducationGoalRepository;

@Service
public class EducationGoalService {
	
	@Autowired
	private EducationGoalRepository repository;
			
	public Iterable<EducationGoal> findAll() {
		return repository.findAll();
	}
	
	public Optional<EducationGoal> findOne(Long id) {
		return repository.findById(id);
	}
	
	public boolean existsByTopicID(Long id) {
		return repository.existsByTopicID(id);
	}
	
	public boolean existsByOpis(String opis) {
		return repository.existsByOpis(opis);
	}
	
	public EducationGoal save(EducationGoal object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(EducationGoal object) {
		repository.delete(object);
	}
}
