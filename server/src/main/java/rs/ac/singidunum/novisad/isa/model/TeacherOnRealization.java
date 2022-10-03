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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teacher_on_realization")
public class TeacherOnRealization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int brojCasova;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Professor professor;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_on_realization_type_of_teaching",joinColumns = @JoinColumn(name = "teacher_on_realization_id"),inverseJoinColumns = @JoinColumn(name = "type_of_teaching_id"))
	private Set<TypeOfTeaching> typeOfTeaching = new HashSet<>();
	
	@OneToMany(mappedBy = "teacherOnRealization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SubjectRealization> subjectRealization = new HashSet<SubjectRealization>();
	
	public TeacherOnRealization() {
		super();
	}
	
	public TeacherOnRealization(Long id, int brojCasova) {
		super();
		this.id = id;
		this.brojCasova = brojCasova;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBrojCasova() {
		return brojCasova;
	}

	public void setBrojCasova(int brojCasova) {
		this.brojCasova = brojCasova;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Set<TypeOfTeaching> getTypeOfTeaching() {
		return typeOfTeaching;
	}

	public void setTypeOfTeaching(Set<TypeOfTeaching> typeOfTeaching) {
		this.typeOfTeaching = typeOfTeaching;
	}

	public Set<SubjectRealization> getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(Set<SubjectRealization> subjectRealization) {
		this.subjectRealization = subjectRealization;
	}
}
