package rs.ac.singidunum.novisad.isa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.StudentTests;

@Repository
public interface StudentTestsRepository extends JpaRepository<StudentTests, Long> {
	
	@Query(value = "SELECT * FROM student_tests WHERE student_tests.student_on_the_year_id = :studentOnTheYearID AND student_tests.subject_id = :subjectID", nativeQuery = true)
	Optional<StudentTests> findByStudentOnTheYearAndSubject(@Param("studentOnTheYearID") Long studentOnTheYearID, @Param("subjectID") Long subjectID);

}
