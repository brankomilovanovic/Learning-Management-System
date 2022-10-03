package rs.ac.singidunum.novisad.isa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.TeacherOnRealization;

@Repository
public interface TeacherOnRealizationRepository extends JpaRepository<TeacherOnRealization, Long> {
		
	Iterable<TeacherOnRealization> findByProfessor_id(Long id);


}
