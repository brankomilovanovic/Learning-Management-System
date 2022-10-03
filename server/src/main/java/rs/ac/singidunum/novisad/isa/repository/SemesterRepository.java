package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>{

}
