package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "type_of_topic")
public class TypeOfTopic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TypeTopic naziv;
	
	@OneToMany(mappedBy = "topicType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Topic> topic = new HashSet<Topic>();

	public enum TypeTopic {
		PREDMET,
	    EVALUACIJA_ZNANJA,
	    TERMIN_NASTAVE
	}
	
	public TypeOfTopic() {
		super();
	}
	
	
	public TypeOfTopic(Long id, TypeTopic naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	public TypeOfTopic(TypeTopic naziv) {
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
	
	public Set<Topic> getTopic() {
		return topic;
	}

	public void setTopic(Set<Topic> topic) {
		this.topic = topic;
	}
}
