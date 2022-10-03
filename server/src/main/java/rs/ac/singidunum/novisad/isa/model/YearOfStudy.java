package rs.ac.singidunum.novisad.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="year_of_study")
public class YearOfStudy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date year;
	
	@Column(nullable = false)
	private boolean active;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "year_of_study_subjects", joinColumns = @JoinColumn(name = "year_of_study_id"), inverseJoinColumns = @JoinColumn(name = "subjects_id"))
	private Set<Subject> subjects = new HashSet<>();
	
	@ManyToOne(optional = true)
	@JsonBackReference(value="studyProgramme")
	private StudyProgramme studyProgramme;

	public YearOfStudy(Long id, Date year, boolean active, Set<Subject> subjects, StudyProgramme studyProgramme) {
		super();
		this.id = id;
		this.year = year;
		this.active = active;
		this.subjects = subjects;
		this.studyProgramme = studyProgramme;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

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

	public YearOfStudy() {
		super();
	}

	/*public Set<Subject> getSubject() {
		return subjects;
	}

	public void setSubject(Set<Subject> subjects) {
		this.subjects = subjects;
	}
*/
	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public StudyProgramme getStudyProgramme() {
		return studyProgramme;
	}

	public void setStudyProgramme(StudyProgramme studyProgramme) {
		this.studyProgramme = studyProgramme;
	}

	
}
