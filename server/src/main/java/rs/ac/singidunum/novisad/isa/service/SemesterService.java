package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Semester;
import rs.ac.singidunum.novisad.isa.repository.SemesterRepository;

@Service
public class SemesterService {
	
	@Autowired
	private SemesterRepository semesterRepository;
	
	public Iterable<Semester> findAll() {
		return semesterRepository.findAll();
	}
	
	public Optional<Semester> findOne(Long id) {
		return semesterRepository.findById(id);
	}
	
	public Semester save(Semester semester) {
		return semesterRepository.save(semester);
	}
	
	public void delete(Long id) {
		semesterRepository.deleteById(id);
	}
	
	public void delete(Semester semester) {
		semesterRepository.delete(semester);
	}
}
