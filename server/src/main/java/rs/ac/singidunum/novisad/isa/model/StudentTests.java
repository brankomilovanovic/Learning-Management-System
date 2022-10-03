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
@Table(name="student_tests")
public class StudentTests {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int kolokvijum1;
	
	@Column(nullable = false)
	private int kolokvijum2;
	
	@Column(nullable = false)
	private int ispit;
	
	@Column(nullable = false)
	private int aktivnost;
	
	@Column(nullable = false)
	private int ocena;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private StudentOnTheYear studentOnTheYear;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Subject subject;

	public StudentTests() {
		super();
	}

	public StudentTests(Long id, int kolokvijum1, int kolokvijum2, int ispit, int aktivnost, int ocena,
			StudentOnTheYear studentOnTheYear) {
		super();
		this.id = id;
		this.kolokvijum1 = kolokvijum1;
		this.kolokvijum2 = kolokvijum2;
		this.ispit = ispit;
		this.aktivnost = aktivnost;
		this.ocena = ocena;
		this.studentOnTheYear = studentOnTheYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getKolokvijum1() {
		return kolokvijum1;
	}

	public void setKolokvijum1(int kolokvijum1) {
		this.kolokvijum1 = kolokvijum1;
	}

	public int getKolokvijum2() {
		return kolokvijum2;
	}

	public void setKolokvijum2(int kolokvijum2) {
		this.kolokvijum2 = kolokvijum2;
	}

	public int getIspit() {
		return ispit;
	}

	public void setIspit(int ispit) {
		this.ispit = ispit;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public StudentOnTheYear getStudentOnTheYear() {
		return studentOnTheYear;
	}

	public void setStudentOnTheYear(StudentOnTheYear studentOnTheYear) {
		this.studentOnTheYear = studentOnTheYear;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(int aktivnost) {
		this.aktivnost = aktivnost;
	}
}
