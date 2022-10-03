package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findByNameAndSurname(String name, String surname, String roleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByNameAndSurname(name, surname, roleId, pageable);
    }

    public Optional<User> findOne(Long id)  {
    	return userRepository.findById(id);
    }
    
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

    public User save(User user) {
    	return userRepository.save(user);
    }

    public void delete(Long id) {
    	userRepository.deleteById(id);
    }
    
    public void delete(User user) {
    	userRepository.delete(user);
    }
    
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public int countUser(String name, String surname, String roleId) {
		return userRepository.countUser(name, surname, roleId);
	}
	
//	public Iterable<User> findAll() {
//		return userRepository.findAll();
//	}
//	
//	public Optional<User> findOne(Long id) {
//		return userRepository.findById(id);
//	}
//	
//	
//	public User save(User user) {
//		return userRepository.save(user);
//	}
//	
//	public void delete(Long id) {
//		userRepository.deleteById(id);
//	}
//	
//	public void delete(User user) {
//		userRepository.delete(user);
//	}
}
