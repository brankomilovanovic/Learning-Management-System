package rs.ac.singidunum.novisad.isa.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.forum.Podforum;
import rs.ac.singidunum.novisad.isa.repository.forum.PodforumRepository;

@Service
public class PodforumService {

	@Autowired
	private PodforumRepository repository;
	
	public Iterable<Podforum> findAll() {
		return repository.findAll();
	}
	
	public Optional<Podforum> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Podforum save(Podforum object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(Podforum object) {
		repository.delete(object);
	}
	
	public boolean exsistByNaziv(String naziv) {
		return repository.exsistByName(naziv);
	}
}
