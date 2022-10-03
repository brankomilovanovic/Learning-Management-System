package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.Adress;

public class StudentDTO {
	
	private Long id;
	private String jmbg;
	private Date dateOfBirth;
	private Adress address;
	private String phoneNumber;
    private UserDTO user;
    private StudentOnTheYearDTO student;
	private Set<SubjectNotificationsDTO> subjectNotifications = new HashSet<>();
    
	public StudentOnTheYearDTO getStudent() {
		return student;
	}

	public void setStudent(StudentOnTheYearDTO student) {
		this.student = student;
	}

	public StudentDTO() {
		super();
	}
	
	public StudentDTO(Long id, String jmbg, Date dateOfBirth, Adress address, String phoneNumber) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public StudentDTO(Long id, String jmbg, Date dateOfBirth, Adress address, String phoneNumber, UserDTO user) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.user = user;
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Set<SubjectNotificationsDTO> getSubjectNotifications() {
		return subjectNotifications;
	}

	public void setSubjectNotifications(Set<SubjectNotificationsDTO> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
}
