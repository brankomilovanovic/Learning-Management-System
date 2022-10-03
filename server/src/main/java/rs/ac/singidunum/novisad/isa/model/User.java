package rs.ac.singidunum.novisad.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false, unique = true)
	@Size(max = 30)
	private String username;

	@Column(nullable = false, unique = true)
	@Size(max = 50)
	@Email
	private String email;

	@Column(nullable = false)
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "user")
    private Student student;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "user")
    private Professor professor;

	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "user")
    private KorisnikNaForumu korisnikNaForumu;

	public User() {
	}

	public User(Long id, String name, String surname, String username, String email, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	

	public User(Long id, String name, String surname, @Size(max = 30) String username,
			@Size(max = 50) @Email String email, @Size(max = 120) String password, Student student,
			Professor professor) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.student = student;
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public KorisnikNaForumu getKorisnikNaForumu() {
		return korisnikNaForumu;
	}

	public void setKorisnikNaForumu(KorisnikNaForumu korisnikNaForumu) {
		this.korisnikNaForumu = korisnikNaForumu;
	}
}
