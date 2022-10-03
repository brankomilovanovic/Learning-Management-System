package rs.ac.singidunum.novisad.isa.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ranks")
public class Rank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date electionDate;
	
	@Temporal(TemporalType.DATE)
	@Column()
	private Date terminationDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonBackReference(value="typeRanks")
	private TypeRanks typeRanks;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonBackReference(value="scientificArea")
	private ScientificArea scientificArea;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonBackReference(value="professor")
	private Professor professor;

	public Rank() {
		super();
	}

	public Rank(Long id, Date electionDate, Date terminationDate, TypeRanks typeRanks, ScientificArea scientificArea, Professor professor) {
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

	public TypeRanks getTypeRanks() {
		return typeRanks;
	}

	public void setTypeRanks(TypeRanks typeRanks) {
		this.typeRanks = typeRanks;
	}

	public ScientificArea getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(ScientificArea scientificArea) {
		this.scientificArea = scientificArea;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
