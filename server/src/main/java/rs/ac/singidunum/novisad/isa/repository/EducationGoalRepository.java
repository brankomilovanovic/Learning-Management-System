package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.EducationGoal;

@Repository
public interface EducationGoalRepository extends JpaRepository<EducationGoal, Long> {
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
				 + " FROM education_goals"
				 + " WHERE education_goals.topic_id = :id", nativeQuery = true)
	Boolean existsByTopicID(@Param("id") Long id);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			 + " FROM education_goals"
			 + " WHERE education_goals.opis = :opis", nativeQuery = true)
	Boolean existsByOpis(@Param("opis") String opis);
}
