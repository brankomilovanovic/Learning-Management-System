package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.EvaluationKnowledge;
import rs.ac.singidunum.novisad.isa.repository.EvaluationKnowledgeRepository;

@Service
public class EvaluationKnowledgeService {

	@Autowired
	private EvaluationKnowledgeRepository repository;
	
	public Iterable<EvaluationKnowledge> findAll() {
		return repository.findAll();
	}
	
	public Iterable<EvaluationKnowledge> findAllUndoneTests(Long studentOnTheYearId, Long subjectRealizationId) {
		return repository.findAllUndoneTests(studentOnTheYearId, subjectRealizationId);
	}
	
	public Optional<EvaluationKnowledge> findOne(Long id) {
		return repository.findById(id);
	}
	
	public EvaluationKnowledge save(EvaluationKnowledge object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(EvaluationKnowledge object) {
		repository.delete(object);
	}
}
