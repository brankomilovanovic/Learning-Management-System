package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TeacherOnRealization;
import rs.ac.singidunum.novisad.isa.repository.TeacherOnRealizationRepository;

@Service
public class TeacherOnRealizationService {
	
	@Autowired
	private TeacherOnRealizationRepository repository;
	
	public Iterable<TeacherOnRealization> findAll() {
		return repository.findAll();
	}
	
	public Optional<TeacherOnRealization> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Iterable<TeacherOnRealization> findByProfessorId(Long id) {
		return repository.findByProfessor_id(id);
	}
	
	public TeacherOnRealization save(TeacherOnRealization teacherOnRealization) {
		return repository.save(teacherOnRealization);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(TeacherOnRealization teacherOnRealization) {
		repository.delete(teacherOnRealization);
	}
	

}
