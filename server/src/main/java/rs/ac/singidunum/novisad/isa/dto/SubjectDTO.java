package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.Semester;

public class SubjectDTO {
	
	private Long id;
	private String naziv;
	private int espb;
	private boolean obavezan;
	private int brojPredavanja;
	private int brojVezbi;
	private int drugiObliciNastave;
	private int istrazivackiRad;
	private int ostaliCasovi;
	private String silabus;
    private Set<SubjectNotificationsDTO> subjectNotifications = new HashSet<SubjectNotificationsDTO>();
	private Set<TopicDTO> topic = new HashSet<>();
	private Semester semester;
	
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
	public Set<SubjectNotificationsDTO> getSubjectNotifications() {
		return subjectNotifications;
	}
	public void setSubjectNotifications(Set<SubjectNotificationsDTO> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
	public Set<TopicDTO> getTopic() {
		return topic;
	}
	public void setTopic(Set<TopicDTO> topic) {
		this.topic = topic;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public SubjectDTO(Long id, String naziv, int espb, boolean obavezan, int brojPredavanja, int brojVezbi,
			int drugiObliciNastave, int istrazivackiRad, int ostaliCasovi, String silabus, Set<TopicDTO> topic,
			Semester semester) {
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
		this.semester = semester;
	}
	
	public SubjectDTO() {
		super();
	}
}
