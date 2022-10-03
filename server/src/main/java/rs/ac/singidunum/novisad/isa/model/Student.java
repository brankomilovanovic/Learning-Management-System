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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "students")
public class Student {	
	
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
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "student_subject", joinColumns = @JoinColumn(name = "STUDENT_ID"), inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID"))
	private Set<Subject> subject = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_notifications_student", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_notifications_id"))
	private Set<SubjectNotifications> subjectNotifications = new HashSet<>();

	public Set<Subject> getSubject() {
		return subject;
	}

	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}

	public Student() {
		super();
	}
	
	public Student(Long id, String jmbg, Date dateOfBirth, Adress address, String phoneNumber) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<SubjectNotifications> getSubjectNotifications() {
		return subjectNotifications;
	}

	public void setSubjectNotifications(Set<SubjectNotifications> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
}
