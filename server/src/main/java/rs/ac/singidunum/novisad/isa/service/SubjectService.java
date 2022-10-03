package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	
	public Iterable<Subject> findAll() {
		return subjectRepository.findAll();
	}
	
	public Optional<Subject> findOne(Long id) {
		return subjectRepository.findById(id);
	}
	
	public Iterable<Subject> findByTopic_id(Long id) {
		return subjectRepository.findByTopic_id(id);
	}
	
	public Subject save(Subject subject) {
		return subjectRepository.save(subject);
	}
	
	public void delete(Long id) {
		subjectRepository.deleteById(id);
	}
	
	public void delete(Subject subject) {
		subjectRepository.delete(subject);
	}
	
	public boolean existsByNaziv(String naziv) {
		return subjectRepository.existsByNaziv(naziv);
	}
}
