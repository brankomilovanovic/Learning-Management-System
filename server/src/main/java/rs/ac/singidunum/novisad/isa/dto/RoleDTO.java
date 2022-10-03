package rs.ac.singidunum.novisad.isa.dto;

import rs.ac.singidunum.novisad.isa.model.ERole;

public class RoleDTO {
	
	private Long id;
	private ERole name;
	
	public RoleDTO() {
		super();
	}
	
	public RoleDTO(Long id, ERole name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ERole getName() {
		return name;
	}
	public void setName(ERole name) {
		this.name = name;
	}
}
