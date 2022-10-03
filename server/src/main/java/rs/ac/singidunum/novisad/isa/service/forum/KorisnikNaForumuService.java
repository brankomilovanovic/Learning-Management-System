package rs.ac.singidunum.novisad.isa.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;
import rs.ac.singidunum.novisad.isa.repository.forum.KorisnikNaForumuRepositroy;

@Service
public class KorisnikNaForumuService {
	
	@Autowired
	private KorisnikNaForumuRepositroy repository;
	
	public Iterable<KorisnikNaForumu> findAll() {
		return repository.findAll();
	}

	public Optional<KorisnikNaForumu> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public Optional<KorisnikNaForumu> findOne(Long id) {
		return repository.findById(id);
	}
	
	public KorisnikNaForumu save(KorisnikNaForumu object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(KorisnikNaForumu object) {
		repository.delete(object);
	}
	
	public boolean getFollowPodforumExists(Long id, Long korisnik_id) {
		return repository.getFollowPodforumExists(id, korisnik_id);
	}
	
	public boolean getFollowTemaExists(Long id, Long korisnik_id) {
		return repository.getFollowTemaExists(id, korisnik_id);
	}
	
	public Iterable<KorisnikNaForumu> findAllFollowPodforum(Long id) {
		return repository.getAllFollowPodforum(id);
	}
	
	public Iterable<KorisnikNaForumu> findAllFollowTemu(Long id) {
		return repository.getAllFollowTemu(id);
	}
}
