package rs.ac.singidunum.novisad.isa.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	@Query(value = "SELECT * FROM professors WHERE user_id = :id", nativeQuery = true)
	Optional<Professor> findByUser_id(@Param("id") Long id);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM professors WHERE jmbg = :jmbg", nativeQuery = true)
	Boolean existsByJmbg(@Param("jmbg") String jmbg);
	
	@Query(value = "SELECT professors.*"
				+ " FROM users"
				+ " INNER JOIN professors ON users.id = professors.user_id"
				+ " WHERE users.username = :username", nativeQuery = true)
	Optional<Professor> findByUsername(@Param("username") String username);

	

}
