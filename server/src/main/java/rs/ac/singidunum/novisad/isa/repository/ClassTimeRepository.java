package rs.ac.singidunum.novisad.isa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.ClassTime;

@Repository
public interface ClassTimeRepository extends JpaRepository<ClassTime, Long> {
	
	@Query(value = "SELECT class_times.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN teacher_on_realization ON professors.id = teacher_on_realization.professor_id"
			+ " INNER JOIN subject_realization ON teacher_on_realization.id = subject_realization.teacher_on_realization_id"
			+ " INNER JOIN class_times ON subject_realization.id = class_times.subject_realization_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<ClassTime> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM subjects"
			+ " INNER JOIN subject_realization ON subjects.id = subject_realization.subject_id"
			+ " INNER JOIN class_times ON subject_realization.id = class_times.subject_realization_id"
			+ " WHERE subjects.id = :id AND class_times.type_of_teaching_id = :typeId", nativeQuery = true)
	Boolean existsBySubjectIDWithTypeTeaching(@Param("id") Long id, @Param("typeId") Long typeId);
	
}
