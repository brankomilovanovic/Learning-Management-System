package rs.ac.singidunum.novisad.isa.dto;

public class SemesterDTO {
	
	private Long id;
	private int semester;
	private SubjectDTO subject;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public SemesterDTO(Long id, int semester, SubjectDTO subject) {
		super();
		this.id = id;
		this.semester = semester;
		this.subject = subject;
	}
	public SemesterDTO() {
		super();
	}
}
