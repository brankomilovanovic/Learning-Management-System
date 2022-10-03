package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;


public class TopicDTO {
	
	private Long id;
	private String opis;
	private TypeOfTopicDTO topicType;
	private TeachingMaterialDTO teachingMaterial;
	private Set<EvaluationKnowledgeDTO> evaluationKnowledge = new HashSet<EvaluationKnowledgeDTO>();

	public Set<EvaluationKnowledgeDTO> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}
	
	public void setEvaluationKnowledge(Set<EvaluationKnowledgeDTO> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
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

	public TypeOfTopicDTO getTopicType() {
		return topicType;
	}

	public void setTopicType(TypeOfTopicDTO topicType) {
		this.topicType = topicType;
	}

	public TeachingMaterialDTO getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(TeachingMaterialDTO teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
	}

	public TopicDTO(Long id, String opis, TypeOfTopicDTO topicType) {
		super();
		this.id = id;
		this.opis = opis;
		this.topicType = topicType;
	}

	public TopicDTO() {
		super();
	}
}
