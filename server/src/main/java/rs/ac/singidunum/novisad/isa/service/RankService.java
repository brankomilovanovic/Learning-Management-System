package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Rank;
import rs.ac.singidunum.novisad.isa.repository.RankRepository;

@Service
public class RankService {
	
	@Autowired
	private RankRepository repository;
	
	public Iterable<Rank> findAll() {
		return repository.findAll();
	}
	
	public Optional<Rank> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Iterable<Rank> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public Rank save(Rank typeRanks) {
		return repository.save(typeRanks);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(Rank rank) {
		repository.delete(rank);
	}
}
