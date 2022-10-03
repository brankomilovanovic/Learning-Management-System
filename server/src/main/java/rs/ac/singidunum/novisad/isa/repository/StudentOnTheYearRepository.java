package rs.ac.singidunum.novisad.isa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.StudentOnTheYear;

@Repository
public interface StudentOnTheYearRepository extends JpaRepository<StudentOnTheYear, Long>{
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM student_on_the_year"
			+ " WHERE student_on_the_year.index_no = :id", nativeQuery = true)
	Boolean existIndex(@Param("id") String id);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM student_on_the_year"
			+ " WHERE student_on_the_year.student_id = :studentId", nativeQuery = true)
	Boolean existsStudent(@Param("studentId") Long studentId);
	
	@Query(value = "SELECT student_on_the_year.*"
            + " FROM users"
            + " INNER JOIN students ON users.id = students.user_id"
            + " INNER JOIN student_on_the_year ON students.id = student_on_the_year.student_id"
            + " WHERE users.id = :id", nativeQuery = true)
    Optional<StudentOnTheYear> findByUserId(@Param("id") Long id);
}
