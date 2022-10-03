package rs.ac.singidunum.novisad.isa.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "scientific_area")
public class ScientificArea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private boolean active;
	
	@OneToMany(mappedBy = "scientificArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="scientificArea")
    private Set<Rank> rank;

	public ScientificArea() {
		super();
	}

	public ScientificArea(Long id, String name, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Rank> getRank() {
		return rank;
	}

	public void setRank(Set<Rank> rank) {
		this.rank = rank;
	}

}
