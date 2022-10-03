package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.StudentTests;
import rs.ac.singidunum.novisad.isa.repository.StudentTestsRepository;

@Service
public class StudentTestsService {

	@Autowired
	private StudentTestsRepository repository;
	
	public Iterable<StudentTests> findAll() {
		return repository.findAll();
	}
	
	public Optional<StudentTests> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Optional<StudentTests> findByStudentOnTheYearAndSubject(Long studentOnTheYearId, Long subjectId) {
		return repository.findByStudentOnTheYearAndSubject(studentOnTheYearId, subjectId);
	}
	
	public StudentTests save(StudentTests faculty) {
		return repository.save(faculty);
	}
	
	public void delete(StudentTests faculty) {
		repository.delete(faculty);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
