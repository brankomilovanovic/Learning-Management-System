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
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.service.FacultyService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
	
    @Autowired
    FacultyService service;
    
    
    
    @GetMapping("/allFaculty")

//    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')") 

    //@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')") 

    public ResponseEntity<Iterable<FacultyDTO>> getAll() {
        ArrayList<FacultyDTO> fcs = new ArrayList<FacultyDTO>();
        StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        for (Faculty fc : service.findAll()) {
            Set<RoleDTO> roleDTO = new HashSet<>();
            for(Role role: fc.getDean().getUser().getRoles()) { 
                roleDTO.add(new RoleDTO(role.getId(), role.getName()));
            }
            Set<StudyProgrammeDTO> studyProgrammes =new HashSet<StudyProgrammeDTO>();
            for(StudyProgramme sp : fc.getStudyProgrammes()){
                Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
                for(YearOfStudy year : sp.getYearOfStudy()){
                	Set<SubjectDTO> subjects = new HashSet<>();
                	for(Subject sb : year.getSubjects()) {
                		
                        Set<TopicDTO> topics = new HashSet<>();
                    	for(Topic topic: sb.getTopic()) { 
                    		TopicDTO topicDTO = new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv()));
                    		topicDTO.setTeachingMaterial(new TeachingMaterialDTO(topic.getTeachingMaterial().getId(), topic.getTeachingMaterial().getNaziv(), 
                    				topic.getTeachingMaterial().getGodinaIzdavanja(), topic.getTeachingMaterial().getAutori(), 
        						new FileDTO(topic.getTeachingMaterial().getFile().getId(), topic.getTeachingMaterial().getFile().getOpis(), topic.getTeachingMaterial().getFile().getUrl())));

        					topics.add(topicDTO);
        				}
                    	
                        subjectDTO = new SubjectDTO(sb.getId(), sb.getNaziv(), sb.getEspb(), sb.isObavezan(),
                        		sb.getBrojPredavanja(), sb.getBrojVezbi(), sb.getDrugiObliciNastave(), sb.getIstrazivackiRad(),
                        		sb.getOstaliCasovi(), sb.getSilabus(), topics, null);
                        subjects.add(subjectDTO);
                    }
                    yearOfStudyDTO =new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);
                    years.add(yearOfStudyDTO);
                }
                studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                        new ProfessorDTO(
                                sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                                sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
                                new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                        sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)), years, 
                        new FacultyDTO(fc.getId(), fc.getName(), null, null, null, null, null, null));

                studyProgrammes.add(studyProgrammeDTO);
            }
            fcs.add(new FacultyDTO(fc.getId(), fc.getName(), fc.getContactDetails(), fc.getDescription(),
                    new ProfessorDTO(
                            fc.getDean().getId(), fc.getDean().getJmbg(), fc.getDean().getDateOfBirth(), 
                            fc.getDean().getAddress(),fc.getDean().getPhoneNumber(), fc.getDean().getBiography(), 
                            new UserDTO(fc.getDean().getUser().getId(), fc.getDean().getUser().getName(), fc.getDean().getUser().getSurname(),
                                    fc.getDean().getUser().getUsername(), fc.getDean().getUser().getEmail(), fc.getDean().getUser().getPassword(), roleDTO))
                    ,fc.getAddress(), studyProgrammes, 
            		new UniversityDTO(fc.getUniversity().getId(), fc.getUniversity().getName(), null, null, null, null, null, null)));
        }
        return new ResponseEntity<Iterable<FacultyDTO>>(fcs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<FacultyDTO> getOne(@PathVariable("id") Long id) {
      StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
      YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
      SubjectDTO subjectDTO = new SubjectDTO();
      
      Optional<Faculty> faculty = service.findOne(id);
      FacultyDTO facultyDTO = new FacultyDTO();	
      
      	if(faculty.isPresent()) { 
      		
    	  Set<RoleDTO> roleDTO = new HashSet<>();
          for(Role role: faculty.get().getDean().getUser().getRoles()) { 
              roleDTO.add(new RoleDTO(role.getId(), role.getName()));
          }
          Set<StudyProgrammeDTO> studyProgrammes =new HashSet<StudyProgrammeDTO>();
          for(StudyProgramme sp : faculty.get().getStudyProgrammes()){
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
                  yearOfStudyDTO =new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);
                  years.add(yearOfStudyDTO);
              }
              studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                      new ProfessorDTO(
                              sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                              sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
                              new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                      sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)), years, 
                      new FacultyDTO(faculty.get().getId(), faculty.get().getName(), null, null, null, null, null, null));

              studyProgrammes.add(studyProgrammeDTO);
          }
          facultyDTO = new FacultyDTO(faculty.get().getId(), faculty.get().getName(), faculty.get().getContactDetails(), faculty.get().getDescription(),
                  new ProfessorDTO(
                		  faculty.get().getDean().getId(), faculty.get().getDean().getJmbg(), faculty.get().getDean().getDateOfBirth(), 
                		  faculty.get().getDean().getAddress(),faculty.get().getDean().getPhoneNumber(), faculty.get().getDean().getBiography(), 
                          new UserDTO(faculty.get().getDean().getUser().getId(), faculty.get().getDean().getUser().getName(), faculty.get().getDean().getUser().getSurname(),
                        		  faculty.get().getDean().getUser().getUsername(), faculty.get().getDean().getUser().getEmail(), faculty.get().getDean().getUser().getPassword(), roleDTO))
                  ,faculty.get().getAddress(), studyProgrammes, 
          		new UniversityDTO(faculty.get().getUniversity().getId(), faculty.get().getUniversity().getName(), null, null, null, null, null, null));
          
          return new ResponseEntity<FacultyDTO>(facultyDTO, HttpStatus.OK);
      	}
	return new ResponseEntity<FacultyDTO>(HttpStatus.NOT_FOUND);
  }
    
    @GetMapping("/findByUsername/{username}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<FacultyDTO>> findByUsername(@PathVariable("username") String username) {
        ArrayList<FacultyDTO> fcs = new ArrayList<FacultyDTO>();
        StudyProgrammeDTO studyProgrammeDTO = new StudyProgrammeDTO();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        for (Faculty fc : service.findByUsername(username)) {
            Set<RoleDTO> roleDTO = new HashSet<>();
            for(Role role: fc.getDean().getUser().getRoles()) { 
                roleDTO.add(new RoleDTO(role.getId(), role.getName()));
            }
            Set<StudyProgrammeDTO> studyProgrammes =new HashSet<StudyProgrammeDTO>();
            for(StudyProgramme sp : fc.getStudyProgrammes()){
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
                    yearOfStudyDTO =new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjects, null, null);
                    years.add(yearOfStudyDTO);
                }
                studyProgrammeDTO =new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                        new ProfessorDTO(
                                sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                                sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
                                new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                        sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)), years, 
                        new FacultyDTO(fc.getId(), fc.getName(), null, null, null, null, null, null));

                studyProgrammes.add(studyProgrammeDTO);
            }
            fcs.add(new FacultyDTO(fc.getId(), fc.getName(), fc.getContactDetails(), fc.getDescription(),
                    new ProfessorDTO(
                            fc.getDean().getId(), fc.getDean().getJmbg(), fc.getDean().getDateOfBirth(), 
                            fc.getDean().getAddress(),fc.getDean().getPhoneNumber(), fc.getDean().getBiography(), 
                            new UserDTO(fc.getDean().getUser().getId(), fc.getDean().getUser().getName(), fc.getDean().getUser().getSurname(),
                                    fc.getDean().getUser().getUsername(), fc.getDean().getUser().getEmail(), fc.getDean().getUser().getPassword(), roleDTO))
                    ,fc.getAddress(), studyProgrammes, 
            		new UniversityDTO(fc.getUniversity().getId(), fc.getUniversity().getName(), null, null, null, null, null, null)));
        }
        return new ResponseEntity<Iterable<FacultyDTO>>(fcs, HttpStatus.OK);
    }
    
    @PostMapping("/createFaculty")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty){
        try {
            service.save(faculty);
            return new ResponseEntity<Faculty>(faculty, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Faculty>(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Faculty> update(@PathVariable("id") Long id, @RequestBody Faculty changedObject) {
    	Faculty objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<Faculty>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<Faculty>(HttpStatus.NOT_FOUND);
	}
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Faculty> delete(@PathVariable("id") Long facultyId) {
        if (service.findOne(facultyId).isPresent()) {
            service.delete(facultyId);
            return new ResponseEntity<Faculty>(HttpStatus.OK);
        }
        return new ResponseEntity<Faculty>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/existsFacultyNameInUniversity/{objectToChangeID}/{nameFaculty}/{idUniversity}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsFacultyNameInUniversity(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("nameFaculty") String nameFaculty, @PathVariable("idUniversity") Long idUniversity) {
		if (service.existsFacultyNameInUniversity(nameFaculty, idUniversity) == true) {
			Faculty objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getName().toLowerCase().equals(nameFaculty.toLowerCase())) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Faculty with this name already exists at this university!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Faculty with this name already exists at this university!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Faculty with this name don't exists at this university!"));
	}
}
