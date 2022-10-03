package rs.ac.singidunum.novisad.isa.dto;

public class ScientificAreaDTO {
	
	private Long id;
	private String name;
	private boolean active;
	private RankDTO rank;
	
	public ScientificAreaDTO() {
		super();
	}

	public ScientificAreaDTO(Long id, String name, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public RankDTO getRank() {
		return rank;
	}

	public void setRank(RankDTO rank) {
		this.rank = rank;
	}
}
