package rs.ac.singidunum.novisad.isa.model.forum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "forum_tema")
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private int pregleda;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Podforum podforum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private KorisnikNaForumu autor;
	
	@OneToMany(mappedBy = "tema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Objava> objava = new HashSet<Objava>();

	@ManyToMany(mappedBy = "follow_teme")
    private List<KorisnikNaForumu> korisnici_follow_tema = new ArrayList<KorisnikNaForumu>();
	
	public Tema() {
		super();
	}

	public Tema(Long id, String naziv, int pregleda, Podforum podforum, KorisnikNaForumu autor, Set<Objava> objava) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.pregleda = pregleda;
		this.podforum = podforum;
		this.autor = autor;
		this.objava = objava;
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

	public Podforum getPodforum() {
		return podforum;
	}

	public void setPodforum(Podforum podforum) {
		this.podforum = podforum;
	}

	public KorisnikNaForumu getAutor() {
		return autor;
	}

	public void setAutor(KorisnikNaForumu autor) {
		this.autor = autor;
	}

	public Set<Objava> getObjava() {
		return objava;
	}

	public void setObjava(Set<Objava> objava) {
		this.objava = objava;
	}

	public int getPregleda() {
		return pregleda;
	}

	public void setPregleda(int pregleda) {
		this.pregleda = pregleda;
	}

	public List<KorisnikNaForumu> getKorisnici_follow_tema() {
		return korisnici_follow_tema;
	}

	public void setKorisnici_follow_tema(List<KorisnikNaForumu> korisnici_follow_tema) {
		this.korisnici_follow_tema = korisnici_follow_tema;
	}
}