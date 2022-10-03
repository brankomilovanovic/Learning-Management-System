package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="student_service")
public class StudentServices {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private StudentOnTheYear student;
	
	@OneToOne()
	private StudyProgramme studyProgrammes;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "student_service_year_of_study", joinColumns = @JoinColumn(name = "student_service_id"), inverseJoinColumns = @JoinColumn(name = "year_of_study_id"))
	private Set<YearOfStudy> year = new HashSet<>();
	
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

	public StudentServices() {
		super();
	}

	public StudentOnTheYear getStudent() {
		return student;
	}

	public void setStudent(StudentOnTheYear student) {
		this.student = student;
	}

	public StudyProgramme getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(StudyProgramme studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}

	public Set<YearOfStudy> getYear() {
		return year;
	}

	public void setYear(Set<YearOfStudy> year) {
		this.year = year;
	}

	public StudentServices(Long id, StudentOnTheYear student, StudyProgramme studyProgrammes, Set<YearOfStudy> year,
			boolean choosed) {
		super();
		this.id = id;
		this.student = student;
		this.studyProgrammes = studyProgrammes;
		this.year = year;
		this.choosed = choosed;
	}

}
