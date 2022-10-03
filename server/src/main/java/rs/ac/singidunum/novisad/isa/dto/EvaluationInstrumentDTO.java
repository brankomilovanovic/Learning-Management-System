package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

public class EvaluationInstrumentDTO {
	
	private Long id;
	private FileDTO file;
	private String tipTestiranja;
	private Set<EvaluationKnowledgeDTO> evaluationKnowledge = new HashSet<EvaluationKnowledgeDTO>();

	public EvaluationInstrumentDTO() {
		super();
	}
	
	public EvaluationInstrumentDTO(Long id, String tipTestiranja, FileDTO file) {
		super();
		this.id = id;
		this.tipTestiranja = tipTestiranja;
		this.file = file;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public FileDTO getFile() {
		return file;
	}

	public void setFile(FileDTO file) {
		this.file = file;
	}

	public String getTipTestiranja() {
		return tipTestiranja;
	}

	public void setTipTestiranja(String tipTestiranja) {
		this.tipTestiranja = tipTestiranja;
	}

	public Set<EvaluationKnowledgeDTO> getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(Set<EvaluationKnowledgeDTO> evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
}
