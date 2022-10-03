package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import rs.ac.singidunum.novisad.isa.model.Adress;


public class UniversityDTO {


	private Long id;

	private String name;
	
	private String contactDetails;
	
	private String description;
	
	private Date establishmentDate;

	private Adress address;
	
	private Set<FacultyDTO> faculty = new HashSet<FacultyDTO>();
	
	private ProfessorDTO headmaster;

	public UniversityDTO() {
		super();
		
	}


	public UniversityDTO(Long id, String name, String contactDetails, String description, Date establishmentDate,
			Adress address, Set<FacultyDTO> faculty, ProfessorDTO headmaster) {
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

	public Set<FacultyDTO> getFaculty() {
		return faculty;
	}

	public void setFaculty(Set<FacultyDTO> faculty) {
		this.faculty = faculty;
	}

	public ProfessorDTO getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(ProfessorDTO headmaster) {
		this.headmaster = headmaster;
	}
	
	
}
