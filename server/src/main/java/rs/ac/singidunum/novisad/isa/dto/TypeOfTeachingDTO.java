package rs.ac.singidunum.novisad.isa.dto;

import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching.Type;

public class TypeOfTeachingDTO {
	
	private Long id;
	private Type naziv;

	public TypeOfTeachingDTO() {
		super();
	}

	public TypeOfTeachingDTO(Long id, Type naziv) {
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

	public Type getNaziv() {
		return naziv;
	}

	public void setNaziv(Type naziv) {
		this.naziv = naziv;
	}

}
