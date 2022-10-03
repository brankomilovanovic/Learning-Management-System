package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	Boolean existsByNaziv(String naziv);
	
	Iterable<Subject> findByTopic_id(Long id);

}
