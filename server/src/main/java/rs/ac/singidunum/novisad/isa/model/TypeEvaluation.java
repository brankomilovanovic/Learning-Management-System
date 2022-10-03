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
@Table(name = "type_evaluations")
public class TypeEvaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipEvaluacije tipEvaluacije;
	
	@OneToMany(mappedBy = "typeEvaluation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvaluationKnowledge> evaluationKnowledge = new HashSet<EvaluationKnowledge>();
	
	public enum TipEvaluacije {
		KOLOKVIJUM,
	    TEST,
	    PROJEKAT,
	    USMENI_ISPIT
	}

	public TypeEvaluation() {
		super();
	}

	public TypeEvaluation(Long id, TipEvaluacije tipEvaluacije) {
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
}
