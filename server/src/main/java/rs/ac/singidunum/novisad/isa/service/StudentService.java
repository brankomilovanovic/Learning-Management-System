package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Student;
import rs.ac.singidunum.novisad.isa.model.StudentServices;
import rs.ac.singidunum.novisad.isa.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentServiceService st;
	
	public Iterable<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Optional<Student> findOne(Long id) {
		return studentRepository.findById(id);
	}
	
	public Optional<Student> findByUserId(Long id) {
		return studentRepository.findByUser_id(id);
	}
	
	public Optional<Student> findByUsername(String username) {
		return studentRepository.findByUsername(username);
	}
	
	public Iterable<StudentServices> findBySub(String username) {
		return st.findBySub(username);
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public void delete(Long id) {
		studentRepository.deleteById(id);
	}
	
	public void delete(Student student) {
		studentRepository.delete(student);
	}
	
	public boolean existByJmbg(String jmbg) {
		return studentRepository.existsByJmbg(jmbg);
	}
	

}
