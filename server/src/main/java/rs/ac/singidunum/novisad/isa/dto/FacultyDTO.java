package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.Adress;


public class FacultyDTO {

private Long id;
	

	private String name;
	private String contactDetails;
	private String description;
	private ProfessorDTO dean;
	private Adress address;
	private Set<StudyProgrammeDTO> studyProgrammes = new HashSet<StudyProgrammeDTO>();

	private UniversityDTO university;

	public FacultyDTO() {
		super();
		
	}


	public FacultyDTO(Long id, String name, String contactDetails, String description, ProfessorDTO dean,
			Adress address, Set<StudyProgrammeDTO> studyProgrammes, UniversityDTO university) {
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


	public ProfessorDTO getDean() {
		return dean;
	}


	public void setDean(ProfessorDTO dean) {
		this.dean = dean;
	}


	public Adress getAddress() {
		return address;
	}


	public void setAddress(Adress address) {
		this.address = address;
	}


	public UniversityDTO getUniversity() {
		return university;
	}


	public void setUniversity(UniversityDTO university) {
		this.university = university;
	}


	public Set<StudyProgrammeDTO> getStudyProgrammes() {
		return studyProgrammes;
	}


	public void setStudyProgrammes(Set<StudyProgrammeDTO> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}
	
	
}
