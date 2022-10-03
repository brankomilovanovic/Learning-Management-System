package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
	
	private Long id;
	private String name;
	private String surname;
	private String username;
	private String email;
	private String password;
	private Set<RoleDTO> roles = new HashSet<>();
    private StudentDTO student;
    private ProfessorDTO professor;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(Long id, String name, String surname, String username, String email, String password, Set<RoleDTO> roles) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	

	public UserDTO(Long id, String name, String surname, String username, String email, String password,
			Set<RoleDTO> roles, StudentDTO student, ProfessorDTO professor) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
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
	
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	 
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public ProfessorDTO getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}
	
	
}
