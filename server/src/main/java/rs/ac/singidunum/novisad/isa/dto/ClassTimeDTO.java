package rs.ac.singidunum.novisad.isa.dto;

import java.time.LocalDateTime;

public class ClassTimeDTO {

	private Long id;
	private LocalDateTime vremePocetka;
	private LocalDateTime vremeKraja;
	private TopicDTO topic;
	private TypeOfTeachingDTO typeOfTeaching;
	private SubjectRealizationDTO subjectRealization;
	
	public ClassTimeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClassTimeDTO(Long id, LocalDateTime vremePocetka, LocalDateTime vremeKraja, TopicDTO topic, TypeOfTeachingDTO typeOfTeaching, SubjectRealizationDTO subjectRealization) {
		super();
		this.id = id;
		this.vremePocetka = vremePocetka;
		this.vremeKraja = vremeKraja;
		this.topic = topic;
		this.typeOfTeaching = typeOfTeaching;
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
	
	public TopicDTO getTopic() {
		return topic;
	}
	
	public void setTopic(TopicDTO topic) {
		this.topic = topic;
	}
	
	public TypeOfTeachingDTO getTypeOfTeaching() {
		return typeOfTeaching;
	}
	
	public void setTypeOfTeaching(TypeOfTeachingDTO typeOfTeaching) {
		this.typeOfTeaching = typeOfTeaching;
	}

	public SubjectRealizationDTO getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
		this.subjectRealization = subjectRealization;
	}
}
