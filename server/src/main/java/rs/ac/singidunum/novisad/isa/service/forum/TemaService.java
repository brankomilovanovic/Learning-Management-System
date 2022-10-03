package rs.ac.singidunum.novisad.isa.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.forum.Tema;
import rs.ac.singidunum.novisad.isa.repository.forum.TemaRepository;

@Service
public class TemaService {

	@Autowired
	private TemaRepository repository;
	
	public Iterable<Tema> findAll() {
		return repository.findAll();
	}
	
	public Iterable<Tema> findAllByPodforumId(Long id) {
		return repository.findAllByPodforumId(id);
	}
	
	public Optional<Tema> findOne(Long id) {
		return repository.findById(id);
	}
	
	public void dodajPregledTemi(Long id) {
		repository.dodajPregledTemi(id);
	}
	
	public Tema save(Tema object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(Tema object) {
		repository.delete(object);
	}
}
