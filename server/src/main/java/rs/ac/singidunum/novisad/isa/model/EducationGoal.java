package rs.ac.singidunum.novisad.isa.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "education_goals")
public class EducationGoal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String opis;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Topic topic;

	public EducationGoal() {
		super();
	}

	public EducationGoal(Long id, String opis, Topic topic) {
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
