package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private int espb;
	
	@Column(nullable = false)
	private boolean obavezan;
	
	@Column(nullable = false)
	private int brojPredavanja;
	
	@Column(nullable = false)
	private int brojVezbi;
	
	@Column()
	private int drugiObliciNastave;
	
	@Column()
	private int istrazivackiRad;
	
	@Column()
	private int ostaliCasovi;	
	
	@Column()
	private String silabus;

	@ManyToMany()
	@JoinTable(name = "subjects_topic",joinColumns = @JoinColumn(name = "subject_id"),inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private Set<Topic> topic = new HashSet<>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private Set<Semester> semester = new HashSet<Semester>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private Set<StudentTests> studentTests = new HashSet<StudentTests>();
	
	//@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	//private Set<YearOfStudy> yearofstudy = new HashSet<YearOfStudy>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private Set<SubjectRealization> subjectRealization = new HashSet<SubjectRealization>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SubjectNotifications> subjectNotifications = new HashSet<SubjectNotifications>();

	public Subject() {
		super();
	}
	
	public Subject(Long id, String naziv, int espb, boolean obavezan, int brojPredavanja, int brojVezbi,
			int drugiObliciNastave, int istrazivackiRad, int ostaliCasovi, String silabus, Set<Topic> topic) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.espb = espb;
		this.obavezan = obavezan;
		this.brojPredavanja = brojPredavanja;
		this.brojVezbi = brojVezbi;
		this.drugiObliciNastave = drugiObliciNastave;
		this.istrazivackiRad = istrazivackiRad;
		this.ostaliCasovi = ostaliCasovi;
		this.silabus = silabus;
		this.topic = topic;
	}

	public String getSilabus() {
		return silabus;
	}

	public void setSilabus(String silabus) {
		this.silabus = silabus;
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

	public int getEspb() {
		return espb;
	}

	public void setEspb(int espb) {
		this.espb = espb;
	}

	public boolean isObavezan() {
		return obavezan;
	}

	public void setObavezan(boolean obavezan) {
		this.obavezan = obavezan;
	}

	public int getBrojPredavanja() {
		return brojPredavanja;
	}

	public void setBrojPredavanja(int brojPredavanja) {
		this.brojPredavanja = brojPredavanja;
	}

	public int getBrojVezbi() {
		return brojVezbi;
	}

	public void setBrojVezbi(int brojVezbi) {
		this.brojVezbi = brojVezbi;
	}

	public Set<Semester> getSemester() {
		return semester;
	}

	public void setSemester(Set<Semester> semester) {
		this.semester = semester;
	}

	public Set<StudentTests> getStudentTests() {
		return studentTests;
	}

	public void setStudentTests(Set<StudentTests> studentTests) {
		this.studentTests = studentTests;
	}

	public int getDrugiObliciNastave() {
		return drugiObliciNastave;
	}

	public void setDrugiObliciNastave(int drugiObliciNastave) {
		this.drugiObliciNastave = drugiObliciNastave;
	}

	public int getIstrazivackiRad() {
		return istrazivackiRad;
	}

	public void setIstrazivackiRad(int istrazivackiRad) {
		this.istrazivackiRad = istrazivackiRad;
	}

	public int getOstaliCasovi() {
		return ostaliCasovi;
	}

	public void setOstaliCasovi(int ostaliCasovi) {
		this.ostaliCasovi = ostaliCasovi;
	}

	public Set<Topic> getTopic() {
		return topic;
	}

	public void setTopic(Set<Topic> topic) {
		this.topic = topic;
	}

	public Set<SubjectRealization> getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(Set<SubjectRealization> subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public Set<SubjectNotifications> getSubjectNotifications() {
		return subjectNotifications;
	}

	public void setSubjectNotifications(Set<SubjectNotifications> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
}
