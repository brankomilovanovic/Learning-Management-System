package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long>{

	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM universities"
			+ " WHERE universities.name = :name", nativeQuery = true)
	Boolean existByName(@Param("name") String name);
	
	@Query(value = "SELECT universities.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN universities ON professors.id = universities.professor_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<University> findByUsername(@Param("username") String username);
}
