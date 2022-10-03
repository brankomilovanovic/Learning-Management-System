package rs.ac.singidunum.novisad.isa.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.FollowSub;
import rs.ac.singidunum.novisad.isa.repository.FollowSubRepository;

@Service
public class FollowSubService {
	
	@Autowired
	private FollowSubRepository s;
	
	public Iterable<FollowSub> findAll() {
		return s.findAll();
	}
	
	/*public Iterable<FollowSub> findByUsername(String username) {
		return s.findByUsername(username);
	}*/
	
	public Iterable<FollowSub> findByUserId(Long id) {
		return s.findByUser_id(id);
	}
	
	public Iterable<BigInteger> findStudentOnSubject(Long subjectID) {
		return s.findStudentOnSubject(subjectID);
	}
	
	public Optional<FollowSub> findOne(Long id) {
		return s.findById(id);
	}
	
	public FollowSub save(FollowSub yearOfStudy) {
		return s.save(yearOfStudy);
	}
	
	public void delete(Long id) {
		s.deleteById(id);
	}
	
	public void delete(FollowSub yearOfStudy) {
		s.delete(yearOfStudy);
	}
}