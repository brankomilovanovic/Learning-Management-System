package rs.ac.singidunum.novisad.isa.dto;



public class EducationGoalDTO {

	private Long id;
	private String opis;
	private TopicDTO topic;
	
	public EducationGoalDTO() {
		super();
	}
	
	public EducationGoalDTO(Long id, String opis, TopicDTO topic) {
		super();
		this.id = id;
		this.opis = opis;
		this.topic = topic;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOpis() {
		return opis;
	}
	
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public TopicDTO getTopic() {
		return topic;
	}
	
	public void setTopic(TopicDTO topic) {
		this.topic = topic;
	}
}
