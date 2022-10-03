package rs.ac.singidunum.novisad.isa.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class EvaluationKnowledgeDTO {

	private Long id;
	private LocalDateTime vremePocetka;
	private LocalDateTime vremeKraja;
	private int bodovi;

	private TopicDTO topic;
	private EvaluationInstrumentDTO evaluationInstrument;
	private TypeEvaluationDTO typeEvaluation;
	private SubjectRealizationDTO subjectRealization;

    private Set<TakingExamDTO> takingExam;
    
	public EvaluationKnowledgeDTO() {
		super();
	}

	public EvaluationKnowledgeDTO(Long id, LocalDateTime vremePocetka, LocalDateTime vremeKraja, int bodovi, TopicDTO topic,
			EvaluationInstrumentDTO evaluationInstrument, TypeEvaluationDTO typeEvaluation, SubjectRealizationDTO subjectRealization) {
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

	public TopicDTO getTopic() {
		return topic;
	}

	public void setTopic(TopicDTO topic) {
		this.topic = topic;
	}

	public EvaluationInstrumentDTO getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(EvaluationInstrumentDTO evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public TypeEvaluationDTO getTypeEvaluation() {
		return typeEvaluation;
	}

	public void setTypeEvaluation(TypeEvaluationDTO typeEvaluation) {
		this.typeEvaluation = typeEvaluation;
	}

	public Set<TakingExamDTO> getTakingExam() {
		return takingExam;
	}

	public void setTakingExam(Set<TakingExamDTO> takingExam) {
		this.takingExam = takingExam;
	}

	public SubjectRealizationDTO getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
		this.subjectRealization = subjectRealization;
	}
}