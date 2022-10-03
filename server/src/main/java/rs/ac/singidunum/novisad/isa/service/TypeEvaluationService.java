package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TypeEvaluation;
import rs.ac.singidunum.novisad.isa.repository.TypeEvaluationRepository;

@Service
public class TypeEvaluationService {
	
	@Autowired
	private TypeEvaluationRepository repository;
	
	public Iterable<TypeEvaluation> findAll() {
		return repository.findAll();
	}
	
	public Optional<TypeEvaluation> findOne(Long id) {
		return repository.findById(id);
	}
	
	public TypeEvaluation save(TypeEvaluation object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(TypeEvaluation object) {
		repository.delete(object);
	}
}
