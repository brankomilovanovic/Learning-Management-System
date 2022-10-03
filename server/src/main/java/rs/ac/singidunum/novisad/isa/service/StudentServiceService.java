package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.StudentServices;
import rs.ac.singidunum.novisad.isa.repository.StudentServiceRepository;

@Service
public class StudentServiceService {
	
	@Autowired
	private StudentServiceRepository repository;
	
	public Iterable<StudentServices> findBySub(String username) {
		return repository.findByUsername(username);
	}
	
	public Iterable<StudentServices> findById(Long id) {
		return repository.findByIds(id);
	}
	
	public Iterable<StudentServices> findAll() {
		return repository.findAll();
	}
	
	public Optional<StudentServices> findOne(Long id) {
		return repository.findById(id);
	}
	
	public StudentServices save(StudentServices faculty) {
		return repository.save(faculty);
	}
	
	public void delete(StudentServices faculty) {
		repository.delete(faculty);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public boolean existsStudentByStudyProgrammeAndYear(Long studentID, Long studyProgrammeId, Long yearOfStudyId) {
		return repository.existsStudentByStudyProgrammeAndYear(studentID, studyProgrammeId, yearOfStudyId);
	}
}
