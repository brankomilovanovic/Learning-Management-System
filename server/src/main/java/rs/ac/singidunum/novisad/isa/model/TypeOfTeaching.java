package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "type_of_teaching")
public class TypeOfTeaching {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Type naziv;
	
	@OneToMany(mappedBy = "typeOfTeaching", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ClassTime> classTime = new HashSet<ClassTime>();

	public enum Type {
		PREDAVANJA,
	    VEZBE,
	    MENTORSKA_NASTAVA
	}
	
	public TypeOfTeaching() {
		super();
	}
	
	
	public TypeOfTeaching(Long id, Type naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	public TypeOfTeaching(Type naziv) {
		this.naziv = naziv;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getNaziv() {
		return naziv;
	}

	public void setNaziv(Type naziv) {
		this.naziv = naziv;
	}
	
	public Set<ClassTime> getClassTime() {
		return classTime;
	}

	public void setClassTime(Set<ClassTime> classTime) {
		this.classTime = classTime;
	}
}

