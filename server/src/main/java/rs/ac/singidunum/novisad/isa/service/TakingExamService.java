package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TakingExam;
import rs.ac.singidunum.novisad.isa.repository.TakingExamRepository;

@Service
public class TakingExamService {

	@Autowired
	private TakingExamRepository repository;
	
	public Iterable<TakingExam> findAll() {
		return repository.findAll();
	}
	
	public Iterable<TakingExam> findByStudentOnTheYearAndSubject(Long studentOnTheYearID, Long subjectID) {
		return repository.findByStudentOnTheYearAndSubject(studentOnTheYearID, subjectID);
	}
	
	public Optional<TakingExam> findOne(Long id) {
		return repository.findById(id);
	}
	
	public TakingExam save(TakingExam object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(TakingExam object) {
		repository.delete(object);
	}
	
}
