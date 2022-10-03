package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evaluation_instruments")
public class EvaluationInstrument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private File file;
	
	@Column(nullable = false)
	private String tipTestiranja;
	
	@OneToMany(mappedBy = "evaluationInstrument", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvaluationKnowledge> evaluationKnowledge = new HashSet<EvaluationKnowledge>();
	
	public EvaluationInstrument() {
		super();
	}

	public EvaluationInstrument(Long id, String tipTestiranja, File file) {
		super();
		this.id = id;
		this.tipTestiranja = tipTestiranja;
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getTipTestiranja() {
		return tipTestiranja;
	}

	public void setTipTestiranja(String tipTestiranja) {
		this.tipTestiranja = tipTestiranja;
	}
	
}
