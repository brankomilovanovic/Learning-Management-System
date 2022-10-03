package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.EvaluationKnowledge;

public class SubjectRealizationDTO {
	
	private Long id;
	private SubjectDTO subject;
	private TeacherOnRealizationDTO teacherOnRealization;
	private Set<EvaluationKnowledge> evaluationKnowledge = new HashSet<EvaluationKnowledge>();
	
	public SubjectRealizationDTO() {
		super();
	}
	
	public SubjectRealizationDTO(Long id, SubjectDTO subject) {
		super();
		this.id = id;
		this.subject = subject;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public SubjectDTO getSubject() {
		return subject;
	}
	
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	
	public TeacherOnRealizationDTO getTeacherOnRealization() {
		return teacherOnRealization;
	}
	
	public void setTeacherOnRealization(TeacherOnRealizationDTO teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Set<EvaluationKnowledge> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(Set<EvaluationKnowledge> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
}
