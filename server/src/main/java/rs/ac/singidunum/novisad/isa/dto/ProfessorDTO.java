package rs.ac.singidunum.novisad.isa.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rs.ac.singidunum.novisad.isa.model.Adress;
public class ProfessorDTO {
	
	private Long id;
	private String jmbg;
	private Date dateOfBirth;
	private Adress address;
	private String phoneNumber;
	private String biography;
    private UserDTO user;
    private Set<RankDTO> ranks = new HashSet<RankDTO>();
    private Set<TeacherOnRealizationDTO> teacherOnRealization = new HashSet<TeacherOnRealizationDTO>();
    private Set<SubjectNotificationsDTO> subjectNotifications = new HashSet<SubjectNotificationsDTO>();

	public ProfessorDTO() {
		super();
	}

	public ProfessorDTO(Long id, String jmbg, Date dateOfBirth, Adress address, String phoneNumber, String biography, UserDTO user) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.biography = biography;
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

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Set<RankDTO> getRanks() {
		return ranks;
	}

	public void setRanks(Set<RankDTO> ranks) {
		this.ranks = ranks;
	}

	public Set<TeacherOnRealizationDTO> getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(Set<TeacherOnRealizationDTO> teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Set<SubjectNotificationsDTO> getSubjectNotifications() {
		return subjectNotifications;
	}

	public void setSubjectNotifications(Set<SubjectNotificationsDTO> subjectNotifications) {
		this.subjectNotifications = subjectNotifications;
	}
}
