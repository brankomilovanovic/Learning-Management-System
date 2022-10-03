package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;


public class FollowSubDTO {
	
	private Long id;
	private UserDTO user;
	private Set<SubjectDTO> subjects = new HashSet<SubjectDTO>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<SubjectDTO> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
	public FollowSubDTO() {
		super();
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public FollowSubDTO(Long id, UserDTO user, Set<SubjectDTO> subjects) {
		super();
		this.id = id;
		this.user = user;
		this.subjects = subjects;
	}
}
