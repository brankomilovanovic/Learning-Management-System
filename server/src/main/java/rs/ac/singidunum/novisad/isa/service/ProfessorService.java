package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Professor;
import rs.ac.singidunum.novisad.isa.model.SubjectRealization;
import rs.ac.singidunum.novisad.isa.repository.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private SubjectRealizationService subservice;
	
	public Iterable<Professor> findAll() {
		return professorRepository.findAll();
	}
	
	public Optional<Professor> findOne(Long id) {
		return professorRepository.findById(id);
	}
	
	public Iterable<SubjectRealization> findBySubject(String username) {
        return subservice.findByUsername(username);
    }
	
	public Optional<Professor> findByUserId(Long id) {
		return professorRepository.findByUser_id(id);
	}
	
	public Optional<Professor> findByUsername(String username) {
		return professorRepository.findByUsername(username);
	}

	public Professor save(Professor professor) {
		return professorRepository.save(professor);
	}
	
	public void delete(Professor professor) {
		professorRepository.delete(professor);
	}
	
	public void delete(Long id) {
		professorRepository.deleteById(id);
	}
	
	public boolean existByJmbg(String jmbg) {
		return professorRepository.existsByJmbg(jmbg);
	}
	
}
