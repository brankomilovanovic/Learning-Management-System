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
@Table(name = "follow_sub")
public class FollowSub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToMany()
	@JoinTable(name = "follow_sub_subjects",joinColumns = @JoinColumn(name = "follow_sub_id"),inverseJoinColumns = @JoinColumn(name = "subjects_id"))
	private Set<Subject> subjects = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public FollowSub() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FollowSub(Long id, User user, Set<Subject> subjects) {
		super();
		this.id = id;
		this.user = user;
		this.subjects = subjects;
	}
}