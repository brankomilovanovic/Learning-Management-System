package rs.ac.singidunum.novisad.isa.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "class_times")
public class ClassTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime vremePocetka;
	
	@Column(nullable = false)
	private LocalDateTime vremeKraja;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private TypeOfTeaching typeOfTeaching;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private SubjectRealization subjectRealization;

	public ClassTime() {
		super();
	}

	public ClassTime(Long id, LocalDateTime vremePocetka, LocalDateTime vremeKraja, Topic topic, TypeOfTeaching typeOfTeaching, SubjectRealization subjectRealization) {
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public TypeOfTeaching getTypeOfTeaching() {
		return typeOfTeaching;
	}

	public void setTypeOfTeaching(TypeOfTeaching typeOfTeaching) {
		this.typeOfTeaching = typeOfTeaching;
	}

	public SubjectRealization getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealization subjectRealization) {
		this.subjectRealization = subjectRealization;
	}
}
