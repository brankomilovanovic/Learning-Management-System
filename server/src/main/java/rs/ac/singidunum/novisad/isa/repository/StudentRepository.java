package rs.ac.singidunum.novisad.isa.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query(value = "SELECT * FROM students WHERE user_id = :id", nativeQuery = true)
	Optional<Student> findByUser_id(@Param("id") Long id);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM students WHERE jmbg = :jmbg", nativeQuery = true)
	Boolean existsByJmbg(@Param("jmbg") String jmbg);
	
	@Query(value = "SELECT students.*"
			+ " FROM users"
			+ " INNER JOIN students ON users.id = students.user_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Optional<Student> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT students.*"
			+ " FROM users"
			+ " INNER JOIN students ON users.id = students.user_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<Student> findBySub(@Param("username") String username);
	
}
