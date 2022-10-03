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
@Table(name = "subject_notifications")
public class SubjectNotifications {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String opis;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Subject subject;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Professor professor;

	public SubjectNotifications(Long id, String naziv, String opis, Subject subject, Professor professor) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.subject = subject;
		this.professor = professor;
	}

	public SubjectNotifications() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
}
