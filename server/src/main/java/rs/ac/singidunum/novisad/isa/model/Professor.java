package rs.ac.singidunum.novisad.isa.model;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "professors")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column()
	private String jmbg;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column()
	private Adress address;
	
	@Column()
	private String phoneNumber;
	
	@Column()
	private String biography;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="professor")
	private Set<Rank> ranks = new HashSet<Rank>();
	
	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TeacherOnRealization> teacherOnRealization = new HashSet<TeacherOnRealization>();
	
	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SubjectNotifications> subjectNotifications = new HashSet<SubjectNotifications>();

	public Professor() {
		super();
	}

	public Professor(Long id, String jmbg, Date dateOfBirth, Adress address, String phoneNumber, String biography) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.biography = biography;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Adress getAddress() {
		return address;
	}

	public void setAddress(Adress address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(Set<Rank> ranks) {
		this.ranks = ranks;
	}

	public Set<TeacherOnRealization> getTeacherOnRealizations() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealizations(Set<TeacherOnRealization> teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Set<TeacherOnRealization> getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(Set<TeacherOnRealization> teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Set<SubjectNotifications> getSubjectNotifications() {
		return subjectNotifications;
	}

	public void setSubjectNotifications(Set<SubjectNotifications> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
}
