package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.ClassTime;
import rs.ac.singidunum.novisad.isa.repository.ClassTimeRepository;

@Service
public class ClassTimeService {

	@Autowired
	private ClassTimeRepository repository;
	
	public Iterable<ClassTime> findAll() {
		return repository.findAll();
	}
	
	public Optional<ClassTime> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Iterable<ClassTime> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public boolean existsBySubjectID(Long id, Long typeTeachingId) {
		return repository.existsBySubjectIDWithTypeTeaching(id, typeTeachingId);
	}
	
	public ClassTime save(ClassTime object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(ClassTime object) {
		repository.delete(object);
	}
}
