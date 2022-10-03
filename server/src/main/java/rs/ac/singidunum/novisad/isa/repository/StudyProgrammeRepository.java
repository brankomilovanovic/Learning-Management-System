package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.isa.model.StudyProgramme;

@Repository
public interface StudyProgrammeRepository extends JpaRepository<StudyProgramme, Long>{

	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM faculty"
			+ " INNER JOIN study_programmes ON faculty.id = study_programmes.faculty_id"
			+ " WHERE study_programmes.name = :name AND faculty.id = :facultyId", nativeQuery = true)
	Boolean existsByNameInFaculty(@Param("name") String name, @Param("facultyId") Long facultyId);
	
	@Query(value = "SELECT study_programmes.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN study_programmes ON professors.id = study_programmes.director_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<StudyProgramme> findByUsername(@Param("username") String username);
}
