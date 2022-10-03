package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "subject_realization")
public class SubjectRealization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Subject subject;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private TeacherOnRealization teacherOnRealization;
	
	@OneToMany(mappedBy = "subjectRealization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvaluationKnowledge> evaluationKnowledge = new HashSet<EvaluationKnowledge>();
	
	public SubjectRealization() {
		super();
	}

	public SubjectRealization(Long id, Subject subject, TeacherOnRealization teacherOnRealization) {
		super();
		this.id = id;
		this.subject = subject;
		this.teacherOnRealization = teacherOnRealization;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public TeacherOnRealization getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(TeacherOnRealization teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Set<EvaluationKnowledge> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(Set<EvaluationKnowledge> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
}
