package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.dto.forum.ObjavaDTO;

public class FileDTO {
	
	private Long id;
	private String opis;
	private String url;
	private Set<EvaluationInstrumentDTO> evaluationInstrument = new HashSet<EvaluationInstrumentDTO>();
	private Set<TeachingMaterialDTO> teachingMaterial = new HashSet<TeachingMaterialDTO>();
	private ObjavaDTO objava;

	public FileDTO() {
		super();
	}
	
	public FileDTO(Long id, String opis, String url) {
		super();
		this.id = id;
		this.opis = opis;
		this.url = url;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public Set<EvaluationInstrumentDTO> getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(Set<EvaluationInstrumentDTO> evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public Set<TeachingMaterialDTO> getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(Set<TeachingMaterialDTO> teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
	}

	public ObjavaDTO getObjava() {
		return objava;
	}

	public void setObjava(ObjavaDTO objava) {
		this.objava = objava;
	}
}

