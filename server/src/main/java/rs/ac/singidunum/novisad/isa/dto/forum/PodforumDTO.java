package rs.ac.singidunum.novisad.isa.dto.forum;

import java.util.HashSet;
import java.util.Set;

public class PodforumDTO {
	
	private Long id;
	private String naziv;
	private String opis;
	private ForumDTO forum;
	private Set<TemaDTO> tema = new HashSet<TemaDTO>();
	private int totalTema;
	private int totalObjava;

	public PodforumDTO() {
		super();
	}

	public PodforumDTO(Long id, String naziv, String opis, ForumDTO forum, Set<TemaDTO> tema) {
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

	public ForumDTO getForum() {
		return forum;
	}

	public void setForum(ForumDTO forum) {
		this.forum = forum;
	}

	public Set<TemaDTO> getTema() {
		return tema;
	}

	public void setTema(Set<TemaDTO> tema) {
		this.tema = tema;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getTotalTema() {
		return totalTema;
	}

	public void setTotalTema(int totalTema) {
		this.totalTema = totalTema;
	}

	public int getTotalObjava() {
		return totalObjava;
	}

	public void setTotalObjava(int totalObjava) {
		this.totalObjava = totalObjava;
	}
}
