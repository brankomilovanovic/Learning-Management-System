package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.singidunum.novisad.isa.model.EvaluationKnowledge;

public interface EvaluationKnowledgeRepository extends JpaRepository<EvaluationKnowledge, Long> {
	
	@Query(value = "SELECT evaluation_knowledges.* FROM subjects"
			+ " INNER JOIN evaluation_knowledges ON subjects.id = evaluation_knowledges.subject_realization_id"
			+ " WHERE subjects.id = :subjectId AND NOT EXISTS"
			+ " (SELECT * FROM taking_exam WHERE taking_exam.evaluation_knowledge_id = evaluation_knowledges.id AND taking_exam.student_on_the_year_id = :studentOnTheYearId)", nativeQuery = true)
	Iterable<EvaluationKnowledge> findAllUndoneTests(@Param("studentOnTheYearId") Long studentOnTheYearId, @Param("subjectId") Long subjectId);
}
