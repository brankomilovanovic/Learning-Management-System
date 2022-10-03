package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String opis;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private TypeOfTopic topicType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private TeachingMaterial teachingMaterial;
	
	@OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvaluationKnowledge> evaluationKnowledge = new HashSet<EvaluationKnowledge>();
	
	@OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ClassTime> classTime = new HashSet<ClassTime>();
	
	@OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EducationGoal> educationGoal = new HashSet<EducationGoal>();

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

	public Set<EvaluationKnowledge> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(Set<EvaluationKnowledge> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}

	public Set<ClassTime> getClassTime() {
		return classTime;
	}

	public void setClassTime(Set<ClassTime> classTime) {
		this.classTime = classTime;
	}

	public Set<EducationGoal> getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(Set<EducationGoal> educationGoal) {
		this.educationGoal = educationGoal;
	}

	public TypeOfTopic getTopicType() {
		return topicType;
	}

	public void setTopicType(TypeOfTopic topicType) {
		this.topicType = topicType;
	}
	
	public TeachingMaterial getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(TeachingMaterial teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
	}

	public Topic() {
		super();
	}

	public Topic(Long id, String opis, TypeOfTopic topicType, TeachingMaterial teachingMaterial) {
		super();
		this.id = id;
		this.opis = opis;
		this.topicType = topicType;
		this.teachingMaterial = teachingMaterial;
	}

}
