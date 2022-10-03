package rs.ac.singidunum.novisad.isa.model;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "universities")
public class University implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1969506958583805190L;

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
	
	@Temporal(TemporalType.DATE)
	private Date establishmentDate;
	
	@Column(nullable = false)
	private Adress address;

	@OneToMany(mappedBy = "university", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonManagedReference(value="university")
	private Set<Faculty> faculty = new HashSet<Faculty>();
	
	@OneToOne
    @JoinColumn(name = "professor_id", nullable = false)
	@JsonBackReference
	private Professor headmaster;

	public University() {
		super();
		
	}

	public University(Long id, String name, String contactDetails, String description, Date establishmentDate,
			Adress address, Set<Faculty> faculty, Professor headmaster) {
		super();
		this.id = id;
		this.name = name;
		this.contactDetails = contactDetails;
		this.description = description;
		this.establishmentDate = establishmentDate;
		this.address = address;
		this.faculty = faculty;
		this.headmaster = headmaster;
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

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public Adress getAddress() {
		return address;
	}

	public void setAddress(Adress address) {
		this.address = address;
	}

	public Set<Faculty> getFaculty() {
		return faculty;
	}

	public void setFaculty(Set<Faculty> faculty) {
		this.faculty = faculty;
	}

	public Professor getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(Professor headmaster) {
		this.headmaster = headmaster;
	}
	
	
}
