package rs.ac.singidunum.novisad.isa.dto;

import java.util.Set;

public class TeacherOnRealizationDTO {
	
	private Long id;
	private int brojCasova;
	
	private ProfessorDTO professor;
	private Set<TypeOfTeachingDTO> typeOfTeaching;
	private Set<SubjectRealizationDTO> subjectRealization;
	
	public TeacherOnRealizationDTO() {
		super();
	}
	
	public TeacherOnRealizationDTO(Long id, int brojCasova, ProfessorDTO professor, Set<TypeOfTeachingDTO> typeOfTeaching) {
		super();
		this.id = id;
		this.brojCasova = brojCasova;
		this.professor = professor;
		this.typeOfTeaching = typeOfTeaching;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBrojCasova() {
		return brojCasova;
	}

	public void setBrojCasova(int brojCasova) {
		this.brojCasova = brojCasova;
	}

	public ProfessorDTO getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}

	public Set<TypeOfTeachingDTO> getTypeOfTeaching() {
		return typeOfTeaching;
	}

	public void setTypeOfTeaching(Set<TypeOfTeachingDTO> typeOfTeaching) {
		this.typeOfTeaching = typeOfTeaching;
	}
	
	public Set<SubjectRealizationDTO> getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(Set<SubjectRealizationDTO> subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

}
