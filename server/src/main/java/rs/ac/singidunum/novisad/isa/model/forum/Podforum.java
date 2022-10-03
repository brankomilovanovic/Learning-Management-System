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
@Table(name = "forum_podforum")
public class Podforum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String opis;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Forum forum;
	
	@OneToMany(mappedBy = "podforum", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Tema> tema = new HashSet<Tema>();
	
	@ManyToMany(mappedBy = "follow_podforum")
    private List<KorisnikNaForumu> korisnici_follow_podforum = new ArrayList<KorisnikNaForumu>();
	
	public Podforum() {
		super();
	}

	public Podforum(Long id, String naziv, String opis, Forum forum, Set<Tema> tema) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.forum = forum;
		this.tema = tema;
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

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<Tema> getTema() {
		return tema;
	}

	public void setTema(Set<Tema> tema) {
		this.tema = tema;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<KorisnikNaForumu> getKorisnici_follow_podforum() {
		return korisnici_follow_podforum;
	}

	public void setKorisnici_follow_podforum(List<KorisnikNaForumu> korisnici_follow_podforum) {
		this.korisnici_follow_podforum = korisnici_follow_podforum;
	}
}
