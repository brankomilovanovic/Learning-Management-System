package rs.ac.singidunum.novisad.isa.dto;

import rs.ac.singidunum.novisad.isa.model.TypeOfTopic.TypeTopic;

public class TypeOfTopicDTO {
	
	private Long id;
	private TypeTopic naziv;

	public TypeOfTopicDTO() {
		super();
	}

	public TypeOfTopicDTO(Long id, TypeTopic naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeTopic getNaziv() {
		return naziv;
	}

	public void setNaziv(TypeTopic naziv) {
		this.naziv = naziv;
	}
}
