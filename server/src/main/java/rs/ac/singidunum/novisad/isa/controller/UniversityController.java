package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
import rs.ac.singidunum.novisad.isa.dto.FacultyDTO;
import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.StudyProgrammeDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.TeachingMaterialDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.dto.UniversityDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.YearOfStudyDTO;
import rs.ac.singidunum.novisad.isa.model.Faculty;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.StudyProgramme;
import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.University;
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.service.UniversityService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/universities")
public class UniversityController {
	
    @Autowired
    UniversityService service;
    
    @GetMapping("/allUniversities")
    public ResponseEntity<Iterable<UniversityDTO>> getAll() {
        ArrayList<UniversityDTO> unis = new ArrayList<UniversityDTO>();
        FacultyDTO facultyDTO = new FacultyDTO();
        StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        for (University uni : service.findAll()) {
            Set<RoleDTO> roleDTO = new HashSet<>();
            for(Role role: uni.getHeadmaster().getUser().getRoles()) { 
                roleDTO.add(new RoleDTO(role.getId(), role.getName()));
            }
            Set<FacultyDTO> facs =new HashSet<FacultyDTO>();
            for(Faculty fc : uni.getFaculty()) {
                Set<StudyProgrammeDTO> studyProgrammes = new HashSet<StudyProgrammeDTO>();
                for(StudyProgramme sp : fc.getStudyProgrammes()) {
                	Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
                    for(YearOfStudy year : sp.getYearOfStudy()){
                        Set<SubjectDTO> subjects = new HashSet<>();
                        for(Subject sb : year.getSubjects()) {
                        	
                        	Set<TopicDTO> topics = new HashSet<>();
                        	for(Topic topic: sb.getTopic()) {
                        		TopicDTO topicDTO = new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv()));
                        		if(topic.getTeachingMaterial() != null) {
	                        		topicDTO.setTeachingMaterial(new TeachingMaterialDTO(topic.getTeachingMaterial().getId(), topic.getTeachingMaterial().getNaziv(), 
	                        				topic.getTeachingMaterial().getGodinaIzdavanja(), topic.getTeachingMaterial().getAutori(), 
	            						new FileDTO(topic.getTeachingMaterial().getFile().getId(), topic.getTeachingMaterial().getFile().getOpis(), topic.getTeachingMaterial().getFile().getUrl())));
                        		}
            					topics.add(topicDTO);
            				}
                        	
	                        subjectDTO = new SubjectDTO(sb.getId(), sb.getNaziv(), sb.getEspb(), sb.isObavezan(),
	                        		sb.getBrojPredavanja(), sb.getBrojVezbi(), sb.getDrugiObliciNastave(), sb.getIstrazivackiRad(),
	                        		sb.getOstaliCasovi(), sb.getSilabus(), topics, null);
	                        subjects.add(subjectDTO);
                        }  
                       

                        yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);

                        years.add(yearOfStudyDTO);

                    }
                    studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                            new ProfessorDTO(
                                    sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                                    sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(), 
                                    new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                            sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
                            years, null);
                    studyProgrammes.add(studyProgrammeDTO);
                }
                facultyDTO =new FacultyDTO(fc.getId(), fc.getName(), fc.getContactDetails(), fc.getDescription(),
                        new ProfessorDTO(
                                fc.getDean().getId(), fc.getDean().getJmbg(), fc.getDean().getDateOfBirth(), 
                                fc.getDean().getAddress(),fc.getDean().getPhoneNumber(), fc.getDean().getBiography(), 
                                new UserDTO(fc.getDean().getUser().getId(), fc.getDean().getUser().getName(), fc.getDean().getUser().getSurname(),
                                        fc.getDean().getUser().getUsername(), fc.getDean().getUser().getEmail(), fc.getDean().getUser().getPassword(), roleDTO)), 
                        fc.getAddress(), studyProgrammes,  new UniversityDTO(uni.getId(), uni.getName(), null, null, null, null, null, null));
                facs.add(facultyDTO);
            }
            unis.add(new UniversityDTO(uni.getId(), uni.getName(), uni.getContactDetails(), uni.getDescription(), uni.getEstablishmentDate(), uni.getAddress(), facs, 
                    new ProfessorDTO(
                            uni.getHeadmaster().getId(), uni.getHeadmaster().getJmbg(), uni.getHeadmaster().getDateOfBirth(), 
                            uni.getHeadmaster().getAddress(),uni.getHeadmaster().getPhoneNumber(), uni.getHeadmaster().getBiography(),
                            new UserDTO(uni.getHeadmaster().getUser().getId(), uni.getHeadmaster().getUser().getName(), uni.getHeadmaster().getUser().getSurname(),
                                    uni.getHeadmaster().getUser().getUsername(), uni.getHeadmaster().getUser().getEmail(), uni.getHeadmaster().getUser().getPassword(), roleDTO))));
        }
        return new ResponseEntity<Iterable<UniversityDTO>>(unis, HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<Iterable<UniversityDTO>> getByUsername(@PathVariable("username") String username) {
        ArrayList<UniversityDTO> unis = new ArrayList<UniversityDTO>();
        FacultyDTO facultyDTO = new FacultyDTO();
        StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        for (University uni : service.findByUsername(username)) {
            Set<RoleDTO> roleDTO = new HashSet<>();
            for(Role role: uni.getHeadmaster().getUser().getRoles()) { 
                roleDTO.add(new RoleDTO(role.getId(), role.getName()));
            }
            Set<FacultyDTO> facs =new HashSet<FacultyDTO>();
            for(Faculty fc : uni.getFaculty()) {
                Set<StudyProgrammeDTO> studyProgrammes = new HashSet<StudyProgrammeDTO>();
                for(StudyProgramme sp : fc.getStudyProgrammes()) {
                	Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
                    for(YearOfStudy year : sp.getYearOfStudy()){
                        Set<SubjectDTO> subjects = new HashSet<>();
                        for(Subject sb : year.getSubjects()) {
                            Set<TopicDTO> topics = new HashSet<>();
                        	for(Topic topic: sb.getTopic()) { 
                        		topics.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
            				}
	                        subjectDTO = new SubjectDTO(sb.getId(), sb.getNaziv(), sb.getEspb(), sb.isObavezan(),
	                        		sb.getBrojPredavanja(), sb.getBrojVezbi(), sb.getDrugiObliciNastave(), sb.getIstrazivackiRad(),
	                        		sb.getOstaliCasovi(), sb.getSilabus(), topics, null);
	                        subjects.add(subjectDTO);
                        }  
                       

                        yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);

                        years.add(yearOfStudyDTO);

                    }
                    studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                            new ProfessorDTO(
                                    sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                                    sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(), 
                                    new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                            sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
                            years, null);
                    studyProgrammes.add(studyProgrammeDTO);
                }
                facultyDTO =new FacultyDTO(fc.getId(), fc.getName(), fc.getContactDetails(), fc.getDescription(),
                        new ProfessorDTO(
                                fc.getDean().getId(), fc.getDean().getJmbg(), fc.getDean().getDateOfBirth(), 
                                fc.getDean().getAddress(),fc.getDean().getPhoneNumber(), fc.getDean().getBiography(), 
                                new UserDTO(fc.getDean().getUser().getId(), fc.getDean().getUser().getName(), fc.getDean().getUser().getSurname(),
                                        fc.getDean().getUser().getUsername(), fc.getDean().getUser().getEmail(), fc.getDean().getUser().getPassword(), roleDTO)), 
                        fc.getAddress(), studyProgrammes,  new UniversityDTO(uni.getId(), uni.getName(), null, null, null, null, null, null));
                facs.add(facultyDTO);
            }
            unis.add(new UniversityDTO(uni.getId(), uni.getName(), uni.getContactDetails(), uni.getDescription(), uni.getEstablishmentDate(), uni.getAddress(), facs, 
                    new ProfessorDTO(
                            uni.getHeadmaster().getId(), uni.getHeadmaster().getJmbg(), uni.getHeadmaster().getDateOfBirth(), 
                            uni.getHeadmaster().getAddress(),uni.getHeadmaster().getPhoneNumber(), uni.getHeadmaster().getBiography(),
                            new UserDTO(uni.getHeadmaster().getUser().getId(), uni.getHeadmaster().getUser().getName(), uni.getHeadmaster().getUser().getSurname(),
                                    uni.getHeadmaster().getUser().getUsername(), uni.getHeadmaster().getUser().getEmail(), uni.getHeadmaster().getUser().getPassword(), roleDTO))));
        }
        return new ResponseEntity<Iterable<UniversityDTO>>(unis, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<UniversityDTO> getOne(@PathVariable("id") Long id) {
        FacultyDTO facultyDTO = new FacultyDTO();
        StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        
        Optional<University> university = service.findOne(id);
        UniversityDTO universityDTO = new UniversityDTO();
      	if(university.isPresent()) { 
      		
      		Set<RoleDTO> roleDTO = new HashSet<>();
            for(Role role: university.get().getHeadmaster().getUser().getRoles()) { 
                roleDTO.add(new RoleDTO(role.getId(), role.getName()));
            }
            Set<FacultyDTO> facs =new HashSet<FacultyDTO>();
            for(Faculty fc : university.get().getFaculty()) {
                Set<StudyProgrammeDTO> studyProgrammes = new HashSet<StudyProgrammeDTO>();
                for(StudyProgramme sp : fc.getStudyProgrammes()) {
                	Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
                    for(YearOfStudy year : sp.getYearOfStudy()){
                        Set<SubjectDTO> subjects = new HashSet<>();
                        for(Subject sb : year.getSubjects()) {
                            Set<TopicDTO> topics = new HashSet<>();
                        	for(Topic topic: sb.getTopic()) { 
                        		topics.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
            				}
	                        subjectDTO = new SubjectDTO(sb.getId(), sb.getNaziv(), sb.getEspb(), sb.isObavezan(),
	                        		sb.getBrojPredavanja(), sb.getBrojVezbi(), sb.getDrugiObliciNastave(), sb.getIstrazivackiRad(),
	                        		sb.getOstaliCasovi(), null, topics, null);
	                        subjects.add(subjectDTO);
                        }  
                       

                        yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);

                        years.add(yearOfStudyDTO);

                    }
                    studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                            new ProfessorDTO(
                                    sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                                    sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(), 
                                    new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                            sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
                            years, null);
                    studyProgrammes.add(studyProgrammeDTO);
                }
                facultyDTO =new FacultyDTO(fc.getId(), fc.getName(), fc.getContactDetails(), fc.getDescription(),
                        new ProfessorDTO(
                                fc.getDean().getId(), fc.getDean().getJmbg(), fc.getDean().getDateOfBirth(), 
                                fc.getDean().getAddress(),fc.getDean().getPhoneNumber(), fc.getDean().getBiography(), 
                                new UserDTO(fc.getDean().getUser().getId(), fc.getDean().getUser().getName(), fc.getDean().getUser().getSurname(),
                                        fc.getDean().getUser().getUsername(), fc.getDean().getUser().getEmail(), fc.getDean().getUser().getPassword(), roleDTO)), 
                        fc.getAddress(), studyProgrammes,  new UniversityDTO(university.get().getId(), university.get().getName(), null, null, null, null, null, null));
                facs.add(facultyDTO);
            }
            universityDTO = new UniversityDTO(university.get().getId(), university.get().getName(), university.get().getContactDetails(), university.get().getDescription(), university.get().getEstablishmentDate(), university.get().getAddress(), facs, 
                    new ProfessorDTO(
                    		university.get().getHeadmaster().getId(), university.get().getHeadmaster().getJmbg(), university.get().getHeadmaster().getDateOfBirth(), 
                    		university.get().getHeadmaster().getAddress(),university.get().getHeadmaster().getPhoneNumber(), university.get().getHeadmaster().getBiography(),
                            new UserDTO(university.get().getHeadmaster().getUser().getId(), university.get().getHeadmaster().getUser().getName(), university.get().getHeadmaster().getUser().getSurname(),
                            		university.get().getHeadmaster().getUser().getUsername(), university.get().getHeadmaster().getUser().getEmail(), university.get().getHeadmaster().getUser().getPassword(), roleDTO)));
          
          return new ResponseEntity<UniversityDTO>(universityDTO, HttpStatus.OK);
      	}
	return new ResponseEntity<UniversityDTO>(HttpStatus.NOT_FOUND);
  }
    
    @PostMapping("/createUniversity")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<University> create(@RequestBody University university){
        try {
            service.save(university);
            return new ResponseEntity<University>(university, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<University>(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<University> update(@PathVariable("id") Long universityId, @RequestBody University changedUni) {
        University uni = service.findOne(universityId).orElse(null);
        if (uni != null) {
            service.save(changedUni);
            return new ResponseEntity<University>(changedUni, HttpStatus.OK);
        }
        return new ResponseEntity<University>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<University> delete(@PathVariable("id") Long universityId) {
        if (service.findOne(universityId).isPresent()) {
            service.delete(universityId);
            return new ResponseEntity<University>(HttpStatus.OK);
        }
        return new ResponseEntity<University>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/existByName/{objectToChangeID}/{name}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existByName(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("name") String name) {
		if (service.existByName(name) == true) {
			University objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getName().toLowerCase().equals(name.toLowerCase())) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: University with that name already exists!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: University with that name already exists!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
