package rs.ac.singidunum.novisad.isa.dto.forum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.dto.FileDTO;

public class ObjavaDTO {
	
	private Long id;
	private LocalDateTime vremePostavljanja;
	private String sadrzaj;
	private TemaDTO tema;
	private Set<FileDTO> prilozi = new HashSet<FileDTO>();
	private KorisnikNaForumuDTO autor;
	
	public ObjavaDTO() {
		super();
	}

	public ObjavaDTO(Long id, LocalDateTime vremePostavljanja, String sadrzaj, TemaDTO tema, Set<FileDTO> prilozi,
			KorisnikNaForumuDTO autor) {
		super();
		this.id = id;
		this.vremePostavljanja = vremePostavljanja;
		this.sadrzaj = sadrzaj;
		this.tema = tema;
		this.prilozi = prilozi;
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

	public TemaDTO getTema() {
		return tema;
	}

	public void setTema(TemaDTO tema) {
		this.tema = tema;
	}

	public Set<FileDTO> getPrilozi() {
		return prilozi;
	}

	public void setPrilozi(Set<FileDTO> prilozi) {
		this.prilozi = prilozi;
	}

	public KorisnikNaForumuDTO getAutor() {
		return autor;
	}

	public void setAutor(KorisnikNaForumuDTO autor) {
		this.autor = autor;
	}
}
