package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StudentOnTheYearDTO {
	private Long id;
	private String indexNo;
	private Date dateOfEntry;
	private StudentDTO student;
	private Set<TakingExamDTO> takingExam = new HashSet<TakingExamDTO>();
	private Set<StudentTestsDTO> studentTests = new HashSet<StudentTestsDTO>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	public Set<TakingExamDTO> getTakingExam() {
		return takingExam;
	}
	public void setTakingExam(Set<TakingExamDTO> takingExam) {
		this.takingExam = takingExam;
	}
	
	public Set<StudentTestsDTO> getStudentTests() {
		return studentTests;
	}
	public void setStudentTests(Set<StudentTestsDTO> studentTests) {
		this.studentTests = studentTests;
	}
	public StudentOnTheYearDTO(Long id, String indexNo, Date dateOfEntry, Set<TakingExamDTO> takingExam, StudentDTO student, Set<StudentTestsDTO> studentTests) {
		super();
		this.id = id;
		this.indexNo = indexNo;
		this.dateOfEntry = dateOfEntry;
		this.takingExam = takingExam;
		this.student = student;
		this.studentTests = studentTests;
	}
	public StudentOnTheYearDTO() {
		super();
	}
}
