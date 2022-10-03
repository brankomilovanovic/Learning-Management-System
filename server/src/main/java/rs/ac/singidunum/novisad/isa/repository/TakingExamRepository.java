package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.TakingExam;

@Repository
public interface TakingExamRepository extends JpaRepository<TakingExam, Long>  {
	
	@Query(value = "SELECT taking_exam.*"
			+ " FROM subjects"
			+ " INNER JOIN subject_realization ON subjects.id = subject_realization.subject_id"
			+ " INNER JOIN evaluation_knowledges ON subject_realization.id = evaluation_knowledges.subject_realization_id"
			+ " INNER JOIN taking_exam ON evaluation_knowledges.id = taking_exam.evaluation_knowledge_id"
			+ " WHERE taking_exam.student_on_the_year_id = :studentOnTheYearID AND subjects.id = :subjectID", nativeQuery = true)
	Iterable<TakingExam> findByStudentOnTheYearAndSubject(@Param("studentOnTheYearID") Long studentOnTheYearID, @Param("subjectID") Long subjectID);

}
