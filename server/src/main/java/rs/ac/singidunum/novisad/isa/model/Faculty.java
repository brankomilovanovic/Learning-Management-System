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
@Table(name = "faculty")
public class Faculty implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2027668683897030416L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String contactDetails;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne(optional = false)
	@JsonBackReference
	private Professor dean;
	
	@Column(nullable = true)
	private Adress address;
	
	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonManagedReference(value="faculty")
	private Set<StudyProgramme> studyProgrammes = new HashSet<StudyProgramme>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "university_id", insertable = true)
	@JsonBackReference(value="university")
	private University university;
	
	public Faculty() {
		super();
		
	}

	public Faculty(Long id, String name, String contactDetails, String description, Professor dean, Adress address,
			Set<StudyProgramme> studyProgrammes, University university) {
		super();
		this.id = id;
		this.name = name;
		this.contactDetails = contactDetails;
		this.description = description;
		this.dean = dean;
		this.address = address;
		this.studyProgrammes = studyProgrammes;
		this.university = university;
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

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Professor getDean() {
		return dean;
	}

	public void setDean(Professor dean) {
		this.dean = dean;
	}

	public Adress getAddress() {
		return address;
	}

	public void setAddress(Adress address) {
		this.address = address;
	}

	public Set<StudyProgramme> getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(Set<StudyProgramme> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}


	
	
	
}
