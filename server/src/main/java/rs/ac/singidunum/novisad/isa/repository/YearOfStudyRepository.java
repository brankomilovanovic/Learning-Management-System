package rs.ac.singidunum.novisad.isa.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.YearOfStudy;

@Repository
public interface YearOfStudyRepository extends JpaRepository<YearOfStudy, Long>{
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM study_programmes"
			+ " INNER JOIN year_of_study ON study_programmes.id = year_of_study.study_programme_id"
			+ " WHERE year_of_study.study_programme_id = :id AND year_of_study.year = :date", nativeQuery = true)
	Boolean existsByStudyProgrammeAndDate(@Param("id") Long id, @Param("date") Date date);

}
