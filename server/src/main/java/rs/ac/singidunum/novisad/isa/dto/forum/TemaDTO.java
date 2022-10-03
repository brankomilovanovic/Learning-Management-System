package rs.ac.singidunum.novisad.isa.dto.forum;

import java.util.HashSet;
import java.util.Set;

public class TemaDTO {
	
	private Long id;
	private String naziv;
	private int pregleda;
	private PodforumDTO podforum;
	private KorisnikNaForumuDTO autor;
	private Set<ObjavaDTO> objava = new HashSet<ObjavaDTO>();
	
	public TemaDTO() {
		super();
	}

	public TemaDTO(Long id, String naziv, int pregleda, PodforumDTO podforum, KorisnikNaForumuDTO autor, Set<ObjavaDTO> objava) {
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

	public PodforumDTO getPodforum() {
		return podforum;
	}

	public void setPodforum(PodforumDTO podforum) {
		this.podforum = podforum;
	}

	public KorisnikNaForumuDTO getAutor() {
		return autor;
	}

	public void setAutor(KorisnikNaForumuDTO autor) {
		this.autor = autor;
	}

	public Set<ObjavaDTO> getObjava() {
		return objava;
	}

	public void setObjava(Set<ObjavaDTO> objava) {
		this.objava = objava;
	}

	public int getPregleda() {
		return pregleda;
	}

	public void setPregleda(int pregleda) {
		this.pregleda = pregleda;
	}
}
