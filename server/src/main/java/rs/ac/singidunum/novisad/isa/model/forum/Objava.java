package rs.ac.singidunum.novisad.isa.model.forum;

import java.time.LocalDateTime;
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

import rs.ac.singidunum.novisad.isa.model.File;

@Entity
@Table(name = "forum_objava")
public class Objava {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime vremePostavljanja;
	
	@Column(nullable = false)
	private String sadrzaj;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Tema tema;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private KorisnikNaForumu autor;
	
	@OneToMany(mappedBy = "objava", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<File> prilozi = new HashSet<File>();

	public Objava() {
		super();
	}

	public Objava(Long id, LocalDateTime vremePostavljanja, String sadrzaj, Tema tema, KorisnikNaForumu autor) {
		super();
		this.id = id;
		this.vremePostavljanja = vremePostavljanja;
		this.sadrzaj = sadrzaj;
		this.tema = tema;
		this.autor = autor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVremePostavljanja() {
		return vremePostavljanja;
	}

	public void setVremePostavljanja(LocalDateTime vremePostavljanja) {
		this.vremePostavljanja = vremePostavljanja;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Set<File> getPrilozi() {
		return prilozi;
	}

	public void setPrilozi(Set<File> prilozi) {
		this.prilozi = prilozi;
	}

	public KorisnikNaForumu getAutor() {
		return autor;
	}

	public void setAutor(KorisnikNaForumu autor) {
		this.autor = autor;
	}
}
