package rs.ac.singidunum.novisad.isa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "study_programmes")
public class StudyProgramme implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5685361343778281888L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne(optional = false)
	@JsonBackReference
	private Professor director;
	
	@OneToMany(mappedBy = "studyProgramme", cascade = CascadeType.ALL)
	@JsonManagedReference(value="studyProgramme")
	private Set<YearOfStudy> yearOfStudy= new HashSet<YearOfStudy>();
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "faculty_id", insertable = true)
	@JsonBackReference(value="faculty")
	private Faculty faculty;

	public StudyProgramme() {
		super();
		
	}

	public StudyProgramme(Long id, String name, String description,Professor director, Set<YearOfStudy> yearOfStudy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.director = director;
		this.yearOfStudy = yearOfStudy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Professor getDirector() {
		return director;
	}

	public void setDirector(Professor director) {
		this.director = director;
	}

	

	public Set<YearOfStudy> getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Set<YearOfStudy> yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

}
