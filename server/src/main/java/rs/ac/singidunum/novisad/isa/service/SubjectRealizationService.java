package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.SubjectRealization;
import rs.ac.singidunum.novisad.isa.repository.SubjectRealizationRepository;

@Service
public class SubjectRealizationService {
	
	@Autowired
	private SubjectRealizationRepository repository;
	
	public Iterable<SubjectRealization> findAll() {
		return repository.findAll();
	}
	
	public Iterable<SubjectRealization> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public boolean existsSubjectByTypeOfTeaching(Long subjectId, Long typeOfTeachingId) {
		return repository.existsSubjectByTypeOfTeaching(subjectId, typeOfTeachingId);
	}
	
	public Optional<SubjectRealization> findOne(Long id) {
		return repository.findById(id);
	}
	
	public SubjectRealization save(SubjectRealization subjectRealization) {
		return repository.save(subjectRealization);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(SubjectRealization subjectRealization) {
		repository.delete(subjectRealization);
	}
}
