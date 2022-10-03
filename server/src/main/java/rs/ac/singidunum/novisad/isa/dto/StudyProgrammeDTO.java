package rs.ac.singidunum.novisad.isa.dto;

import java.util.Set;

public class StudyProgrammeDTO {

	private Long id;
	private String name;
	private String description;
	private ProfessorDTO director;
	private Set<YearOfStudyDTO> yearOfStudy;
	private FacultyDTO faculty;

	public StudyProgrammeDTO() {
		super();
		
	}

	

	public StudyProgrammeDTO(Long id, String name, String description, ProfessorDTO director, Set<YearOfStudyDTO> yearOfStudy, FacultyDTO faculty
			) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.director = director;
		this.yearOfStudy = yearOfStudy;
		this.faculty = faculty;
		
	}

	public StudyProgrammeDTO(Long id, String name, ProfessorDTO director, Set<YearOfStudyDTO> yearOfStudy
			) {
		super();
		this.id = id;
		this.name = name;
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



	public ProfessorDTO getDirector() {
		return director;
	}

	public void setDirector(ProfessorDTO director) {
		this.director = director;
	}

	

	public Set<YearOfStudyDTO> getYearOfStudy() {
		return yearOfStudy;
	}



	public void setYearOfStudy(Set<YearOfStudyDTO> yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}



	public FacultyDTO getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyDTO faculty) {
		this.faculty = faculty;
	}
	
	
	
}
