package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.StudentServices;

@Repository
public interface StudentServiceRepository extends JpaRepository<StudentServices, Long>{
	
	@Query(value = "SELECT CASE WHEN COUNT(*) >= 1 THEN 'true' ELSE 'false' END"
			+ " FROM student_service"
			+ " INNER JOIN student_service_year_of_study ON student_service.id = student_service_year_of_study.student_service_id"
			+ " WHERE student_service.student_id = :studentId AND student_service.study_programmes_id = :studyProgrammeId AND student_service_year_of_study.year_of_study_id = :yearOfStudyId", nativeQuery = true)
	Boolean existsStudentByStudyProgrammeAndYear(@Param("studentId") Long studentId, @Param("studyProgrammeId") Long studyProgrammeId, @Param("yearOfStudyId") Long yearOfStudyId);
	
	@Query(value = "SELECT student_service.*"
			+ " FROM users"
			+ " INNER JOIN students ON users.id = students.user_id"
			+ " INNER JOIN student_on_the_year ON students.id = student_on_the_year.student_id"
			+ " INNER JOIN student_service ON student_on_the_year.id = student_service.student_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<StudentServices> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT student_service.*"
			+ " FROM users"
			+ " INNER JOIN students ON users.id = students.user_id"
			+ " INNER JOIN student_on_the_year ON students.id = student_on_the_year.student_id"
			+ " INNER JOIN student_service ON student_on_the_year.id = student_service.student_id"
			+ " WHERE users.id = :id", nativeQuery = true)
	Iterable<StudentServices> findByIds(@Param("id") Long id);
	
	
}
