package rs.ac.singidunum.novisad.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RankDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.ScientificAreaDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TeacherOnRealizationDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTeachingDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeRanksDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Professor;
import rs.ac.singidunum.novisad.isa.model.Rank;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.SubjectRealization;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;
import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.service.ProfessorService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/professors")
public class ProfessorController {
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	UserService userService;
	
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	Date dateOfBirth;
	
	@GetMapping("/allProfessors")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<ProfessorDTO>> getAllProfessors() {
		ArrayList<ProfessorDTO> professors = new ArrayList<ProfessorDTO>();
		for (Professor professor : professorService.findAll()) {
			
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: professor.getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			professors.add(new ProfessorDTO(professor.getId(), professor.getJmbg(), professor.getDateOfBirth(), 
					professor.getAddress(), professor.getPhoneNumber(), professor.getBiography(), 
					new UserDTO(professor.getUser().getId(), professor.getUser().getName(), professor.getUser().getSurname(),
							professor.getUser().getUsername(), professor.getUser().getEmail(), professor.getUser().getPassword(), roleDTO)));
		}
		return new ResponseEntity<Iterable<ProfessorDTO>>(professors, HttpStatus.OK);
	}
	
	// ODAVDE IDE DALJE ZA DOBAVLJANJE PREDMETA KOD PROFESORA
	@GetMapping("/subjects/{username}")
	public ResponseEntity<Iterable<SubjectRealizationDTO>> getAll(@PathVariable("username") String username) {
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		
		for (SubjectRealization subjectRealization : professorService.findBySubject(username)) {
			
			/////SUBJECT
			Set<TopicDTO> topicsDTO = new HashSet<>();
			for(Topic topic: subjectRealization.getSubject().getTopic()) { 
				topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
			}
			SubjectDTO subjectDTO = new SubjectDTO(subjectRealization.getSubject().getId(), subjectRealization.getSubject().getNaziv(), subjectRealization.getSubject().getEspb(), 
					subjectRealization.getSubject().isObavezan(),subjectRealization.getSubject().getBrojPredavanja(), subjectRealization.getSubject().getBrojVezbi(), 
						subjectRealization.getSubject().getDrugiObliciNastave(), subjectRealization.getSubject().getIstrazivackiRad(), subjectRealization.getSubject().getOstaliCasovi(), subjectRealization.getSubject().getSilabus(), topicsDTO, null);
			
			///TYPE OF TEACHING
			Set<TypeOfTeachingDTO> typeOfTeachingDTO = new HashSet<>();
			for(TypeOfTeaching typeOfTeaching: subjectRealization.getTeacherOnRealization().getTypeOfTeaching()) { 
				typeOfTeachingDTO.add(new TypeOfTeachingDTO(typeOfTeaching.getId(), typeOfTeaching.getNaziv()));
			}
			
			////TEACHER ON REALIZATION
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: subjectRealization.getTeacherOnRealization().getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professorDTO = new ProfessorDTO(subjectRealization.getTeacherOnRealization().getProfessor().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getJmbg(), 
					subjectRealization.getTeacherOnRealization().getProfessor().getDateOfBirth(), subjectRealization.getTeacherOnRealization().getProfessor().getAddress(), 
						subjectRealization.getTeacherOnRealization().getProfessor().getPhoneNumber(), subjectRealization.getTeacherOnRealization().getProfessor().getBiography(), 
						new UserDTO(subjectRealization.getTeacherOnRealization().getProfessor().getUser().getId(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getName(), 
							subjectRealization.getTeacherOnRealization().getProfessor().getUser().getSurname(),subjectRealization.getTeacherOnRealization().getProfessor().getUser().getUsername(), 
								subjectRealization.getTeacherOnRealization().getProfessor().getUser().getEmail(), subjectRealization.getTeacherOnRealization().getProfessor().getUser().getPassword(), roleDTO));
			
			TeacherOnRealizationDTO teacherOnRealizationDTO = new TeacherOnRealizationDTO(subjectRealization.getTeacherOnRealization().getId(), subjectRealization.getTeacherOnRealization().getBrojCasova(), 
					professorDTO, typeOfTeachingDTO);
			
			///SUBJECT REALIZATION
			SubjectRealizationDTO subjectRealizationDTO = new SubjectRealizationDTO(subjectRealization.getId(), subjectDTO);
			subjectRealizationDTO.setTeacherOnRealization(teacherOnRealizationDTO);
			////CREATE SUBJECT ON REALIZATION
			subjectRealizations.add(subjectRealizationDTO);
		}

		return new ResponseEntity<Iterable<SubjectRealizationDTO>>(subjectRealizations, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable("userId") Long userId) {
		Optional<Professor> professor = professorService.findByUserId(userId);
		ProfessorDTO professorDTO = new ProfessorDTO();	
		if (professor.isPresent()) {
			
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: professor.get().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getJmbg(), professor.get().getDateOfBirth(), 
					professor.get().getAddress(), professor.get().getPhoneNumber(), professor.get().getBiography(),
					new UserDTO(professor.get().getUser().getId(), professor.get().getUser().getName(), professor.get().getUser().getSurname(),
							professor.get().getUser().getUsername(), professor.get().getUser().getEmail(), professor.get().getUser().getPassword(), roleDTO));
			
			Set<RankDTO> ranksDTO = new HashSet<>();
			for(Rank rank: professor.get().getRanks()) { 
			ranksDTO.add(new RankDTO(rank.getId(), rank.getElectionDate(), rank.getTerminationDate(),
					new TypeRanksDTO(rank.getTypeRanks().getId(), rank.getTypeRanks().getName(), rank.getTypeRanks().isActive()),
						new ScientificAreaDTO(rank.getScientificArea().getId(), rank.getScientificArea().getName(), rank.getScientificArea().isActive()), null));
			}
			
			professorDTO.setRanks(ranksDTO);
			
			return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ProfessorDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getByUsername/{username}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<ProfessorDTO> getByUsername(@PathVariable("username") String username) {
		Optional<Professor> professor = professorService.findByUsername(username);
		ProfessorDTO professorDTO = new ProfessorDTO();	
		if (professor.isPresent()) {
			
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: professor.get().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getJmbg(), professor.get().getDateOfBirth(), 
					professor.get().getAddress(), professor.get().getPhoneNumber(), professor.get().getBiography(),
					new UserDTO(professor.get().getUser().getId(), professor.get().getUser().getName(), professor.get().getUser().getSurname(),
							professor.get().getUser().getUsername(), professor.get().getUser().getEmail(), professor.get().getUser().getPassword(), roleDTO));
			
			Set<RankDTO> ranksDTO = new HashSet<>();
			for(Rank rank: professor.get().getRanks()) { 
			ranksDTO.add(new RankDTO(rank.getId(), rank.getElectionDate(), rank.getTerminationDate(),
					new TypeRanksDTO(rank.getTypeRanks().getId(), rank.getTypeRanks().getName(), rank.getTypeRanks().isActive()),
						new ScientificAreaDTO(rank.getScientificArea().getId(), rank.getScientificArea().getName(), rank.getScientificArea().isActive()), null));
			}
			
			professorDTO.setRanks(ranksDTO);
			
			return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ProfessorDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/createProfessor")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Professor> createProfessor(@RequestBody Professor newProfessor){;
		try {
			Optional<User> user = userService.findByUsername(newProfessor.getUser().getUsername());
			newProfessor.setUser(user.get());
			professorService.save(newProfessor);
			return new ResponseEntity<Professor>(newProfessor, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<Professor>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PROFESSOR')")
	public ResponseEntity<Professor> update(@PathVariable("id") Long id, @RequestBody Professor changedObject) {
		Professor professorExist = professorService.findOne(id).orElse(null);
		if (professorExist != null) {
			professorService.save(changedObject);
			return new ResponseEntity<Professor>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<Professor>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<ProfessorDTO> deleteProfessor(@PathVariable("userId") Long userId) {
		Optional<Professor> professor = professorService.findByUserId(userId);
		if (professor.isPresent()) {
			professorService.delete(professor.get().getId());
			return new ResponseEntity<ProfessorDTO>(HttpStatus.OK);
		}
		return new ResponseEntity<ProfessorDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/checkJmbg/{userId}/{jmbg}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> checkJmbg(@PathVariable("userId") String userId, @PathVariable("jmbg") String jmbg) {
		if (professorService.existByJmbg(jmbg) == true) {
			if(!userId.equals("null")) {
				try {
				Optional<Professor> professor = professorService.findOne(Long.parseLong(userId));
				if(!jmbg.equals(professor.get().getJmbg())) { return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!")); }
				} 
				catch(NoSuchElementException e) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!"));	
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: JMBG is already taken!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("JMBG is free!"));
	}

}
