package rs.ac.singidunum.novisad.isa.dto.forum;

import java.util.HashSet;
import java.util.Set;

public class ForumDTO {
	
	private Long id;
	private boolean javni;
	private Set<PodforumDTO> podforum = new HashSet<PodforumDTO>();
	
	public ForumDTO() {
		super();
	}

	public ForumDTO(Long id, boolean javni, Set<PodforumDTO> podforum) {
		super();
		this.id = id;
		this.javni = javni;
		this.podforum = podforum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isJavni() {
		return javni;
	}

	public void setJavni(boolean javni) {
		this.javni = javni;
	}

	public Set<PodforumDTO> getPodforum() {
		return podforum;
	}

	public void setPodforum(Set<PodforumDTO> podforum) {
		this.podforum = podforum;
	}
}
