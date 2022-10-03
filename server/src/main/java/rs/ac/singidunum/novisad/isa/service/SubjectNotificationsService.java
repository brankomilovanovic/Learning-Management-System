package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;
import rs.ac.singidunum.novisad.isa.repository.SubjectNotificationsRepository;

@Service
public class SubjectNotificationsService {

	@Autowired
	private SubjectNotificationsRepository repository;
	
	public Iterable<SubjectNotifications> findAll() {
		return repository.findAll();
	}

	public Iterable<SubjectNotifications> findBySubjectId(Long subjectId) {
		return repository.findBySubjectID(subjectId);
	}
	
	public Optional<SubjectNotifications> findOne(Long id) {
		return repository.findById(id);
	}
	
	public SubjectNotifications save(SubjectNotifications object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(SubjectNotifications object) {
		repository.delete(object);
	}
}
