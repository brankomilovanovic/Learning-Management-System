package rs.ac.singidunum.novisad.isa.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.repository.YearOfStudyRepository;

@Service
public class YearOfStudyService {

	@Autowired
	private YearOfStudyRepository yearOfStudyRepository;
	
	public Iterable<YearOfStudy> findAll() {
		return yearOfStudyRepository.findAll();
	}
	
	public Optional<YearOfStudy> findOne(Long id) {
		return yearOfStudyRepository.findById(id);
	}
	
	public boolean existsByStudyProgrammeAndDate(Long id, Date date) {
		return yearOfStudyRepository.existsByStudyProgrammeAndDate(id, date);
	}
	
	public YearOfStudy save(YearOfStudy yearOfStudy) {
		return yearOfStudyRepository.save(yearOfStudy);
	}
	
	public void delete(Long id) {
		yearOfStudyRepository.deleteById(id);
	}
	
	public void delete(YearOfStudy yearOfStudy) {
		yearOfStudyRepository.delete(yearOfStudy);
	}
}
