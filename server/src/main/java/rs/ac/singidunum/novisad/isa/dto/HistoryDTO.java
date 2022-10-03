package rs.ac.singidunum.novisad.isa.dto;

import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.StudentTests;

public class HistoryDTO {
	
	private Long id;
	private UserDTO user;
	private Set<StudentTests> tests = new HashSet<>();
	private Set<StudentServicesDTO> enroll = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public HistoryDTO() {
		super();
	}
	public Set<StudentServicesDTO> getEnroll() {
		return enroll;
	}
	public void setEnroll(Set<StudentServicesDTO> enroll) {
		this.enroll = enroll;
	}
	public Set<StudentTests> getTests() {
		return tests;
	}
	public void setTests(Set<StudentTests> tests) {
		this.tests = tests;
	}
	public HistoryDTO(Long id, UserDTO user, Set<StudentTests> tests, Set<StudentServicesDTO> enroll) {
		super();
		this.id = id;
		this.user = user;
		this.tests = tests;
		this.enroll = enroll;
	}
	
}
