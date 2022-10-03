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

import rs.ac.singidunum.novisad.isa.model.forum.Objava;

@Entity
@Table(name = "files")
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String opis;
	
	@Column(nullable = false)
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Objava objava;
	
	@OneToMany(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvaluationInstrument> evaluationInstrument = new HashSet<EvaluationInstrument>();
	
	@OneToMany(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TeachingMaterial> teachingMaterial = new HashSet<TeachingMaterial>();
	
	public File() {
		super();
	}

	public File(Long id, String opis, String url) {
		super();
		this.id = id;
		this.opis = opis;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<EvaluationInstrument> getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(Set<EvaluationInstrument> evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public Set<TeachingMaterial> getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(Set<TeachingMaterial> teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
	}

	public Objava getObjava() {
		return objava;
	}

	public void setObjava(Objava objava) {
		this.objava = objava;
	}
}
