package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.TypeEvaluation.TipEvaluacije;

public class TypeEvaluationDTO {

	private Long id;
	private TipEvaluacije tipEvaluacije;
	private Set<EvaluationKnowledgeDTO> evaluationKnowledge = new HashSet<EvaluationKnowledgeDTO>();

	public TypeEvaluationDTO() {
		super();
	}
	
	public TypeEvaluationDTO(Long id, TipEvaluacije tipEvaluacije) {
		super();
		this.id = id;
		this.tipEvaluacije = tipEvaluacije;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public TipEvaluacije getTipEvaluacije() {
		return tipEvaluacije;
	}
	
	public void setTipEvaluacije(TipEvaluacije tipEvaluacije) {
		this.tipEvaluacije = tipEvaluacije;
	}

	public Set<EvaluationKnowledgeDTO> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(Set<EvaluationKnowledgeDTO> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
}
