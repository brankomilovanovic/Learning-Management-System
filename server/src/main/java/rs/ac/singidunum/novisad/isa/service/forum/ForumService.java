package rs.ac.singidunum.novisad.isa.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.forum.Forum;
import rs.ac.singidunum.novisad.isa.repository.forum.ForumRepository;

@Service
public class ForumService {

	@Autowired
	private ForumRepository repository;
	
	public Iterable<Forum> findAll() {
		return repository.findAll();
	}
	
	public Optional<Forum> findForum() {
		return repository.findForum();
	}
	
	public Optional<Forum> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Forum save(Forum object) {
		return repository.save(object);
	}
	
	public void updateVidljivost(boolean vidljivost) {
		repository.updateVidljivost(vidljivost);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(Forum object) {
		repository.delete(object);
	}
}
