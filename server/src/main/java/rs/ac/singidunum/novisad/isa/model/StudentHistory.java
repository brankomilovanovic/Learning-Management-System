package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "history")
public class StudentHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToMany()
	@JoinTable(name = "history_tests",joinColumns = @JoinColumn(name = "history_id"),inverseJoinColumns = @JoinColumn(name = "student_tests_id"))
	private Set<StudentTests> tests = new HashSet<>();
	
	@ManyToMany()
	@JoinTable(name = "history_service",joinColumns = @JoinColumn(name = "history_id"),inverseJoinColumns = @JoinColumn(name = "student_service_id"))
	private Set<StudentServices> enroll = new HashSet<>();

	public StudentHistory() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<StudentServices> getEnroll() {
		return enroll;
	}

	public void setEnroll(Set<StudentServices> enroll) {
		this.enroll = enroll;
	}

	public Set<StudentTests> getTests() {
		return tests;
	}

	public void setTests(Set<StudentTests> tests) {
		this.tests = tests;
	}

	public StudentHistory(Long id, User user, Set<StudentTests> tests, Set<StudentServices> enroll) {
		super();
		this.id = id;
		this.user = user;
		this.tests = tests;
		this.enroll = enroll;
	}
	
}
