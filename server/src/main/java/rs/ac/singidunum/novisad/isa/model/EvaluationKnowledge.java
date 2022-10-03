package rs.ac.singidunum.novisad.isa.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evaluation_knowledges")
public class EvaluationKnowledge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime vremePocetka;
	
	@Column(nullable = false)
	private LocalDateTime vremeKraja;
	
	@Column(nullable = false)
	private int bodovi;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private EvaluationInstrument evaluationInstrument;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private TypeEvaluation typeEvaluation;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private SubjectRealization subjectRealization;
	
	@OneToMany(mappedBy = "evaluationKnowledge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TakingExam> takingExam = new HashSet<TakingExam>();
	

	public EvaluationKnowledge() {
		super();
	}

	public EvaluationKnowledge(Long id, LocalDateTime vremePocetka, LocalDateTime vremeKraja, int bodovi, Topic topic,
			EvaluationInstrument evaluationInstrument, TypeEvaluation typeEvaluation, SubjectRealization subjectRealization) {
		super();
		this.id = id;
		this.vremePocetka = vremePocetka;
		this.vremeKraja = vremeKraja;
		this.bodovi = bodovi;
		this.topic = topic;
		this.evaluationInstrument = evaluationInstrument;
		this.typeEvaluation = typeEvaluation;
		this.subjectRealization = subjectRealization;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVremePocetka() {
		return vremePocetka;
	}

	public void setVremePocetka(LocalDateTime vremePocetka) {
		this.vremePocetka = vremePocetka;
	}

	public LocalDateTime getVremeKraja() {
		return vremeKraja;
	}

	public void setVremeKraja(LocalDateTime vremeKraja) {
		this.vremeKraja = vremeKraja;
	}

	public int getBodovi() {
		return bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public EvaluationInstrument getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(EvaluationInstrument evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public TypeEvaluation getTypeEvaluation() {
		return typeEvaluation;
	}

	public void setTypeEvaluation(TypeEvaluation typeEvaluation) {
		this.typeEvaluation = typeEvaluation;
	}

	public Set<TakingExam> getTakingExam() {
		return takingExam;
	}

	public void setTakingExam(Set<TakingExam> takingExam) {
		this.takingExam = takingExam;
	}

	public SubjectRealization getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealization subjectRealization) {
		this.subjectRealization = subjectRealization;
	}
}
