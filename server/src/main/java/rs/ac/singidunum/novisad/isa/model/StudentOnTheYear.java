package rs.ac.singidunum.novisad.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="student_on_the_year")
public class StudentOnTheYear {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column()
	private String indexNo;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfEntry;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Student student;
	
	@OneToMany(mappedBy = "studentOnTheYear", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TakingExam> takingExam = new HashSet<TakingExam>();

	@OneToMany(mappedBy = "studentOnTheYear", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<StudentTests> studentTests = new HashSet<StudentTests>();

	
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Set<TakingExam> getTakingExam() {
		return takingExam;
	}

	public void setTakingExam(Set<TakingExam> takingExam) {
		this.takingExam = takingExam;
	}

	public Set<StudentTests> getStudentTests() {
		return studentTests;
	}

	public void setStudentTests(Set<StudentTests> studentTests) {
		this.studentTests = studentTests;
	}

	public StudentOnTheYear(Long id, String indexNo, Date dateOfEntry, Student student) {
		super();
		this.id = id;
		this.indexNo = indexNo;
		this.dateOfEntry = dateOfEntry;
		this.student = student;
		
	}

	public StudentOnTheYear() {
		super();
	}

	
}
