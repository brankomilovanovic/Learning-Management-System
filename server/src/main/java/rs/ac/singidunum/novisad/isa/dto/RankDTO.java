package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;

public class RankDTO {
	
	private Long id;
	private Date electionDate;
	private Date terminationDate;
	private TypeRanksDTO typeRanks;
	private ScientificAreaDTO scientificArea;
	private ProfessorDTO professor;
	
	public RankDTO() {
		super();
	}
	
	public RankDTO(Long id, Date electionDate, Date terminationDate, TypeRanksDTO typeRanks, ScientificAreaDTO scientificArea, ProfessorDTO professor) {
		super();
		this.id = id;
		this.electionDate = electionDate;
		this.terminationDate = terminationDate;
		this.typeRanks = typeRanks;
		this.scientificArea = scientificArea;
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getElectionDate() {
		return electionDate;
	}

	public void setElectionDate(Date electionDate) {
		this.electionDate = electionDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public TypeRanksDTO getTypeRanks() {
		return typeRanks;
	}

	public void setTypeRanks(TypeRanksDTO typeRanks) {
		this.typeRanks = typeRanks;
	}

	public ScientificAreaDTO getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(ScientificAreaDTO scientificArea) {
		this.scientificArea = scientificArea;
	}

	public ProfessorDTO getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}
}
