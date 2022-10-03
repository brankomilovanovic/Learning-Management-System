package rs.ac.singidunum.novisad.isa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "teaching_materials")
public class TeachingMaterial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date godinaIzdavanja;
	
	@ElementCollection
    @CollectionTable(name = "teaching_materials_authors",joinColumns=@JoinColumn(name = "id", referencedColumnName = "id"))
    @Column(name="authors")
	private List<String> autori = new ArrayList<String>();
    	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private File file;
	
	@OneToMany(mappedBy = "teachingMaterial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Topic> topic = new HashSet<Topic>();

	public TeachingMaterial() {
		super();
	}

	public TeachingMaterial(Long id, String naziv, Date godinaIzdavanja, List<String> autori, File file) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.godinaIzdavanja = godinaIzdavanja;
		this.autori = autori;
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getGodinaIzdavanja() {
		return godinaIzdavanja;
	}

	public void setGodinaIzdavanja(Date godinaIzdavanja) {
		this.godinaIzdavanja = godinaIzdavanja;
	}

	public List<String> getAutori() {
		return autori;
	}

	public void setAutori(List<String> autori) {
		this.autori = autori;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Set<Topic> getTopic() {
		return topic;
	}

	public void setTopic(Set<Topic> topic) {
		this.topic = topic;
	}
}
