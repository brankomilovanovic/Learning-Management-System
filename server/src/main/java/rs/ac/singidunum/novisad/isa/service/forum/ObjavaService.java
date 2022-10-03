package rs.ac.singidunum.novisad.isa.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.forum.Objava;
import rs.ac.singidunum.novisad.isa.repository.forum.ObjavaRepository;

@Service
public class ObjavaService {

	@Autowired
	private ObjavaRepository repository;
	
	public Iterable<Objava> findAll() {
		return repository.findAll();
	}
	
	public Iterable<Objava> findAllForTema(Long id) {
		return repository.findAllForTema(id);
	}
	
	public Optional<Objava> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Objava> findLastPostForSubforum(Long id) {
		return repository.findLastPostForSubforum(id);
	}
	
	public Optional<Objava> findLastPostForTopic(Long id) {
		return repository.findLastPostForTopic(id);
	}
	
	public Optional<Objava> findFirstObjavaForTopic(Long id) {
		return repository.findFirstObjavaForTopic(id);
	}
	
	public Objava save(Objava object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(Objava object) {
		repository.delete(object);
	}
}
