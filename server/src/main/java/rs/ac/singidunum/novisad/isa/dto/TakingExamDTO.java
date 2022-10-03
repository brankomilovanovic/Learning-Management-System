package rs.ac.singidunum.novisad.isa.dto;

public class TakingExamDTO {
	
	private Long id;
	private int bodovi;
	private String napomena;
	private StudentOnTheYearDTO studentOnTheYear;
    private EvaluationKnowledgeDTO evaluationKnowledge;

	public TakingExamDTO() {
		super();
	}
	
	public TakingExamDTO(Long id, int bodovi, String napomena, StudentOnTheYearDTO studentOnTheYear,EvaluationKnowledgeDTO evaluationKnowledge) {
		super();
		this.id = id;
		this.bodovi = bodovi;
		this.napomena = napomena;
		this.studentOnTheYear = studentOnTheYear;
		this.evaluationKnowledge = evaluationKnowledge;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getBodovi() {
		return bodovi;
	}
	
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	
	public String getNapomena() {
		return napomena;
	}
	
	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	
	public StudentOnTheYearDTO getStudentOnTheYear() {
		return studentOnTheYear;
	}
	
	public void setStudentOnTheYear(StudentOnTheYearDTO studentOnTheYear) {
		this.studentOnTheYear = studentOnTheYear;
	}

	public EvaluationKnowledgeDTO getEvaluationKnowledge() {
		return evaluationKnowledge;
	}

	public void setEvaluationKnowledge(EvaluationKnowledgeDTO evaluationKnowledge) {
		this.evaluationKnowledge = evaluationKnowledge;
	}
	
}
