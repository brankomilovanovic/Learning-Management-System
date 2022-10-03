package rs.ac.singidunum.novisad.isa.dto;

public class SubjectNotificationsDTO {

	private Long id;
	private String naziv;
	private String opis;
	private SubjectDTO subject;
	private ProfessorDTO professor;
	
	public SubjectNotificationsDTO() {
		super();
	}
	
	public SubjectNotificationsDTO(Long id, String naziv, String opis, SubjectDTO subject, ProfessorDTO professor) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.subject = subject;
		this.professor = professor;
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
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public ProfessorDTO getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}
}
