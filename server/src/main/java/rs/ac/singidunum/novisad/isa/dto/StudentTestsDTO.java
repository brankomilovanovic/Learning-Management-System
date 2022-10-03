package rs.ac.singidunum.novisad.isa.dto;

public class StudentTestsDTO {

	private Long id;
	private int kolokvijum1;
	private int kolokvijum2;
	private int ispit;
	private int aktivnost;
	private int ocena;
	private StudentOnTheYearDTO studentOnTheYear;
	private SubjectDTO subject;
	
	public StudentTestsDTO() {
		super();
	}

	public StudentTestsDTO(Long id, int kolokvijum1, int kolokvijum2, int ispit, int aktivnost, int ocena, StudentOnTheYearDTO studentOnTheYear, SubjectDTO subject) {
		super();
		this.id = id;
		this.kolokvijum1 = kolokvijum1;
		this.kolokvijum2 = kolokvijum2;
		this.ispit = ispit;
		this.aktivnost = aktivnost;
		this.ocena = ocena;
		this.studentOnTheYear = studentOnTheYear;
		this.subject = subject;
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

	public StudentOnTheYearDTO getStudentOnTheYear() {
		return studentOnTheYear;
	}

	public void setStudentOnTheYear(StudentOnTheYearDTO studentOnTheYear) {
		this.studentOnTheYear = studentOnTheYear;
	}

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public int getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(int aktivnost) {
		this.aktivnost = aktivnost;
	}
}
