package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class YearOfStudyDTO {
	private Long id;
	private Date year;
	private boolean active;	
	private Set<SubjectDTO> subjects =new HashSet<SubjectDTO>();
	private Set<StudentOnTheYearDTO> studentOnTheYear=new HashSet<StudentOnTheYearDTO>();
	private StudyProgrammeDTO studyProgramme;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public YearOfStudyDTO(Long id, Date year, boolean active, Set<SubjectDTO> subjects,
			Set<StudentOnTheYearDTO> studentOnTheYear, StudyProgrammeDTO studyProgramme) {
		super();
		this.id = id;
		this.year = year;
		this.active = active;
		this.subjects = subjects;
		this.studentOnTheYear = studentOnTheYear;
		this.studyProgramme = studyProgramme;
	}
	public StudyProgrammeDTO getStudyProgramme() {
		return studyProgramme;
	}
	public void setStudyProgramme(StudyProgrammeDTO studyProgramme) {
		this.studyProgramme = studyProgramme;
	}
	public Set<SubjectDTO> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
	public Set<StudentOnTheYearDTO> getStudentOnTheYear() {
		return studentOnTheYear;
	}
	public void setStudentOnTheYear(Set<StudentOnTheYearDTO> studentOnTheYear) {
		this.studentOnTheYear = studentOnTheYear;
	}
	public YearOfStudyDTO() {
		super();
	}
}
