package rs.ac.singidunum.novisad.isa.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeachingMaterialDTO {

	private Long id;
	private String naziv;
	private Date godinaIzdavanja;
	private List<String> autori = new ArrayList<String>();
	private FileDTO file;
	private Set<TopicDTO> topic = new HashSet<TopicDTO>();

	public TeachingMaterialDTO() {
		super();
	}
	
	public TeachingMaterialDTO(Long id, String naziv, Date godinaIzdavanja, List<String> autori, FileDTO file) {
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
	
	public FileDTO getFile() {
		return file;
	}
	
	public void setFile(FileDTO file) {
		this.file = file;
	}

	public Set<TopicDTO> getTopic() {
		return topic;
	}

	public void setTopic(Set<TopicDTO> topic) {
		this.topic = topic;
	}
	
}
