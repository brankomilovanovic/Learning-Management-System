package rs.ac.singidunum.novisad.isa.model.forum;

import java.util.HashSet;
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

@Entity
@Table(name = "forum")
public class Forum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private boolean javni;

	@OneToMany(mappedBy = "forum", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Podforum> podforum = new HashSet<Podforum>();
	
	public Forum(Long id, boolean javni) {
		super();
		this.id = id;
		this.javni = javni;
	}

	public Forum() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isJavni() {
		return javni;
	}

	public void setJavni(boolean javni) {
		this.javni = javni;
	}

	public Set<Podforum> getPodforum() {
		return podforum;
	}

	public void setPodforum(Set<Podforum> podforum) {
		this.podforum = podforum;
	}
}
