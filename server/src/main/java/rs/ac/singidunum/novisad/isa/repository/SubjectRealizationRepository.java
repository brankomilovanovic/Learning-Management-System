package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.SubjectRealization;

@Repository
public interface SubjectRealizationRepository extends JpaRepository<SubjectRealization, Long> {
		
	@Query(value = "SELECT subject_realization.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN teacher_on_realization ON professors.id = teacher_on_realization.professor_id"
			+ " INNER JOIN subject_realization ON teacher_on_realization.id = subject_realization.teacher_on_realization_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<SubjectRealization> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM teacher_on_realization"
			+ " INNER JOIN teacher_on_realization_type_of_teaching ON teacher_on_realization.id = teacher_on_realization_type_of_teaching.teacher_on_realization_id"
			+ " INNER JOIN subject_realization ON teacher_on_realization.id = subject_realization.teacher_on_realization_id"
			+ " INNER JOIN type_of_teaching ON teacher_on_realization_type_of_teaching.type_of_teaching_id = type_of_teaching.id"
			+ " WHERE subject_realization.subject_id = :subjectId AND type_of_teaching.id = :typeOfTeachingId", nativeQuery = true)
	Boolean existsSubjectByTypeOfTeaching(@Param("subjectId") Long subjectId, @Param("typeOfTeachingId") Long typeOfTeachingId);
}
