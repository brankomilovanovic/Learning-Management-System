package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.StudentOnTheYear;
import rs.ac.singidunum.novisad.isa.repository.StudentOnTheYearRepository;

@Service
public class StudentOnTheYearService {
	
	@Autowired
	private StudentOnTheYearRepository studentOnTheYearRepository;
	
	public Iterable<StudentOnTheYear> findAll() {
		return studentOnTheYearRepository.findAll();
	}
	
	public Optional<StudentOnTheYear> findStudent(Long id) {
		return studentOnTheYearRepository.findByUserId(id);
	}
	
	public Optional<StudentOnTheYear> findOne(Long id) {
		return studentOnTheYearRepository.findById(id);
	}
	
	public boolean existIndex(String id) {
		return studentOnTheYearRepository.existIndex(id);
	}
	
	public boolean existsStudent(Long studentId) {
		return studentOnTheYearRepository.existsStudent(studentId);
	}
	
	public StudentOnTheYear save(StudentOnTheYear studentOnTheYear) {
		return studentOnTheYearRepository.save(studentOnTheYear);
	}
	
	public void delete(Long id) {
		studentOnTheYearRepository.deleteById(id);
	}
	
	public void delete(StudentOnTheYear studentOnTheYear) {
		studentOnTheYearRepository.delete(studentOnTheYear);
	}
}
