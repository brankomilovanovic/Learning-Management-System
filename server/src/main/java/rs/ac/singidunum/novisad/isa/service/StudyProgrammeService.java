package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.StudyProgramme;
import rs.ac.singidunum.novisad.isa.repository.StudyProgrammeRepository;

@Service
public class StudyProgrammeService {

	@Autowired
	private StudyProgrammeRepository repository;
	
	public Iterable<StudyProgramme> findAll() {
		return repository.findAll();
	}
	
	public Optional<StudyProgramme> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Iterable<StudyProgramme> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public boolean existsByNameInFaculty(String nameFaculty, Long idFaculty) {
		return repository.existsByNameInFaculty(nameFaculty, idFaculty);
	}
	
	public StudyProgramme save(StudyProgramme studyProgramme) {
		return repository.save(studyProgramme);
	}
	
	public void delete(StudyProgramme studyProgramme) {
		repository.delete(studyProgramme);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
