package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TypeOfTopic;
import rs.ac.singidunum.novisad.isa.repository.TypeOfTopicRepository;

@Service
public class TypeOfTopicService {
	
	@Autowired
	private TypeOfTopicRepository repository;
	
	public Iterable<TypeOfTopic> findAll() {
		return repository.findAll();
	}
	
	public Optional<TypeOfTopic> findOne(Long id) {
		return repository.findById(id);
	}
	
	public TypeOfTopic save(TypeOfTopic typeOfTeaching) {
		return repository.save(typeOfTeaching);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(TypeOfTopic typeOfTeaching) {
		repository.delete(typeOfTeaching);
	}
}
