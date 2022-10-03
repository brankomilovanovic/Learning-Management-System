package rs.ac.singidunum.novisad.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "taking_exam")
public class TakingExam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int bodovi;
	
	@Column
	private String napomena;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private StudentOnTheYear studentOnTheYear;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private EvaluationKnowledge evaluationKnowledge;
	
	public TakingExam() {
		super();
	}

	public TakingExam(Long id, int bodovi, String napomena, StudentOnTheYear studentOnTheYear) {
		super();
		this.id = id;
		this.bodovi = bodovi;
		this.napomena = napomena;
		this.studentOnTheYear = studentOnTheYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBodovi() {
		return bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public StudentOnTheYear getStudentOnTheYear() {
		return studentOnTheYear;
	}

	public void setStudentOnTheYear(StudentOnTheYear studentOnTheYear) {
		this.studentOnTheYear = studentOnTheYear;
	}

	public EvaluationKnowledge getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(EvaluationKnowledge evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
}
