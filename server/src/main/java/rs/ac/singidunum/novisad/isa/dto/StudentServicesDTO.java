package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

public class StudentServicesDTO {
	
	public StudentServicesDTO() {
		super();
	}
	
	private Long id;
	private StudentOnTheYearDTO student;
	private StudyProgrammeDTO studyProgrammes;
	private Set<YearOfStudyDTO> year=new HashSet<>();
	private boolean choosed;
	
	public boolean isChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudentOnTheYearDTO getStudent() {
		return student;
	}
	public void setStudent(StudentOnTheYearDTO student) {
		this.student = student;
	}
	public StudyProgrammeDTO getStudyProgrammes() {
		return studyProgrammes;
	}
	public void setStudyProgrammes(StudyProgrammeDTO studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}
	public Set<YearOfStudyDTO> getYear() {
		return year;
	}
	public void setYear(Set<YearOfStudyDTO> year) {
		this.year = year;
	}
	public StudentServicesDTO(Long id, StudentOnTheYearDTO student, StudyProgrammeDTO studyProgrammes,
			Set<YearOfStudyDTO> year, boolean choosed) {
		super();
		this.id = id;
		this.student = student;
		this.studyProgrammes = studyProgrammes;
		this.year = year;
		this.choosed = choosed;
	}
	
	
	
}
