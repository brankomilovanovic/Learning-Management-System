package rs.ac.singidunum.novisad.isa.dto.forum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.dto.UserDTO;

public class KorisnikNaForumuDTO {

	private Long id;
	private LocalDateTime vremePrijavljivanja;
	private int objave;
    private UserDTO user;
	private Set<TemaDTO> tema = new HashSet<TemaDTO>();
	private Set<ObjavaDTO> objava = new HashSet<ObjavaDTO>();
	private Set<TemaDTO> follow_teme = new HashSet<>();
	private Set<PodforumDTO> follow_podforum = new HashSet<>();

	public KorisnikNaForumuDTO() {
		super();
	}
	
	public KorisnikNaForumuDTO(Long id, LocalDateTime vremePrijavljivanja, int objave, UserDTO user) {
		super();
		this.id = id;
		this.vremePrijavljivanja = vremePrijavljivanja;
		this.objave = objave;
		this.user = user;
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Set<TemaDTO> getTema() {
		return tema;
	}
	public void setTema(Set<TemaDTO> tema) {
		this.tema = tema;
	}
	public Set<ObjavaDTO> getObjava() {
		return objava;
	}
	public void setObjava(Set<ObjavaDTO> objava) {
		this.objava = objava;
	}

	public Set<TemaDTO> getFollow_teme() {
		return follow_teme;
	}

	public void setFollow_teme(Set<TemaDTO> follow_teme) {
		this.follow_teme = follow_teme;
	}

	public Set<PodforumDTO> getFollow_podforum() {
		return follow_podforum;
	}

	public void setFollow_podforum(Set<PodforumDTO> follow_podforum) {
		this.follow_podforum = follow_podforum;
	}
}

