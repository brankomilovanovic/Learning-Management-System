package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>{

	@Query(value = "SELECT faculty.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN faculty ON professors.id = faculty.dean_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<Faculty> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM universities"
			+ " INNER JOIN faculty ON universities.id = faculty.university_id"
			+ " WHERE faculty.name = :name AND universities.id = :id", nativeQuery = true)
	Boolean existsFacultyNameInUniversity(@Param("name") String nameFaculty, @Param("id") Long idUniversity);
}
