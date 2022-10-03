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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import rs.ac.singidunum.novisad.isa.model.User;

@Entity
@Table(name = "forum_korisnik")
public class KorisnikNaForumu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime vremePrijavljivanja;
	
	@Column
	private int objave;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "forum_user_follow_tema", joinColumns = @JoinColumn(name = "korisnik_na_forumu_id"), inverseJoinColumns = @JoinColumn(name = "tema_id"))
	private Set<Tema> follow_teme = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "forum_user_follow_podforum", joinColumns = @JoinColumn(name = "korisnik_na_forumu_id"), inverseJoinColumns = @JoinColumn(name = "podforum_id"))
	private Set<Podforum> follow_podforum = new HashSet<>();
	
	@OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Tema> tema = new HashSet<Tema>();
	
	@OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Objava> objava = new HashSet<Objava>();
	
	public KorisnikNaForumu() {
		super();
	}

	public KorisnikNaForumu(Long id, LocalDateTime vremePrijavljivanja, int objave, User user, Set<Tema> tema,
			Set<Objava> objava) {
		super();
		this.id = id;
		this.vremePrijavljivanja = vremePrijavljivanja;
		this.objave = objave;
		this.user = user;
		this.tema = tema;
		this.objava = objava;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVremePrijavljivanja() {
		return vremePrijavljivanja;
	}

	public void setVremePrijavljivanja(LocalDateTime vremePrijavljivanja) {
		this.vremePrijavljivanja = vremePrijavljivanja;
	}

	public int getObjave() {
		return objave;
	}

	public void setObjave(int objave) {
		this.objave = objave;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Tema> getTema() {
		return tema;
	}

	public void setTema(Set<Tema> tema) {
		this.tema = tema;
	}

	public Set<Objava> getObjava() {
		return objava;
	}

	public void setObjava(Set<Objava> objava) {
		this.objava = objava;
	}

	public Set<Tema> getFollow_teme() {
		return follow_teme;
	}

	public void setFollow_teme(Set<Tema> follow_teme) {
		this.follow_teme = follow_teme;
	}

	public Set<Podforum> getFollow_podforum() {
		return follow_podforum;
	}

	public void setFollow_podforum(Set<Podforum> follow_podforum) {
		this.follow_podforum = follow_podforum;
	}
}