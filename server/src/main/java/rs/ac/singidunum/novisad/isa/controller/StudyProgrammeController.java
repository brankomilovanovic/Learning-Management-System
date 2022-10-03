package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentOnTheYearDTO;
import rs.ac.singidunum.novisad.isa.dto.StudyProgrammeDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.YearOfStudyDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.StudyProgramme;
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;

import rs.ac.singidunum.novisad.isa.service.FacultyService;
import rs.ac.singidunum.novisad.isa.service.StudyProgrammeService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/studyProgrammes")
public class StudyProgrammeController {

	@Autowired
    StudyProgrammeService service;
	
	@Autowired
	FacultyService fservice;
	
    @GetMapping("/allStudyProgrammes")
//    @PreAuthorize("hasRole('STUDENT') or hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')") 
    public ResponseEntity<Iterable<StudyProgrammeDTO>> getAll() {
        ArrayList<StudyProgrammeDTO> sps = new ArrayList<StudyProgrammeDTO>();
        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();        
        fservice.findAll();
        for (StudyProgramme sp : service.findAll()) {
            Set<RoleDTO> roleDTO = new HashSet<>();
            if(sp.getDirector().getUser() != null) {
                for(Role role: sp.getDirector().getUser().getRoles()) { 
                    roleDTO.add(new RoleDTO(role.getId(), role.getName()));
                }
            }
            else {
                roleDTO = null;
            }
            Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
            for(YearOfStudy year : sp.getYearOfStudy()){
                Set<StudentOnTheYearDTO> stu =new HashSet<StudentOnTheYearDTO>();
                Set<SubjectDTO> subjectDTO = new HashSet<>();
                for(rs.ac.singidunum.novisad.isa.model.Subject s:year.getSubjects()) {
                	subjectDTO.add(new SubjectDTO(s.getId(),s.getNaziv(),s.getEspb(),s.isObavezan(),s.getBrojPredavanja()
                			,s.getBrojVezbi(),s.getDrugiObliciNastave(),s.getIstrazivackiRad(),s.getOstaliCasovi(), s.getSilabus(), null, null));
                }
                yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjectDTO, stu, null);
                years.add(yearOfStudyDTO);

            }
            sps.add(new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                    new ProfessorDTO(
                        sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                        sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
                            new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                    sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
                    
                    years, new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(), null, null, null, null, null, null)));
        }
        return new ResponseEntity<Iterable<StudyProgrammeDTO>>(sps, HttpStatus.OK);
    }
    
    @GetMapping("/getByUsername/{username}")
//  @PreAuthorize("hasRole('STUDENT') or hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')") 
    public ResponseEntity<Iterable<StudyProgrammeDTO>> getByUsername(@PathVariable("username") String username) {
      ArrayList<StudyProgrammeDTO> sps = new ArrayList<StudyProgrammeDTO>();
      YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();        
      fservice.findAll();
      for (StudyProgramme sp : service.findByUsername(username)) {
          Set<RoleDTO> roleDTO = new HashSet<>();
          if(sp.getDirector().getUser() != null) {
              for(Role role: sp.getDirector().getUser().getRoles()) { 
                  roleDTO.add(new RoleDTO(role.getId(), role.getName()));
              }
          }
          else {
              roleDTO = null;
          }
          Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
          for(YearOfStudy year : sp.getYearOfStudy()){
              Set<StudentOnTheYearDTO> stu =new HashSet<StudentOnTheYearDTO>();
              Set<SubjectDTO> subjectDTO = new HashSet<>();
              for(rs.ac.singidunum.novisad.isa.model.Subject s:year.getSubjects()) {
              	subjectDTO.add(new SubjectDTO(s.getId(),s.getNaziv(),s.getEspb(),s.isObavezan(),s.getBrojPredavanja()
              			,s.getBrojVezbi(),s.getDrugiObliciNastave(),s.getIstrazivackiRad(),s.getOstaliCasovi(), s.getSilabus(), null, null));
              }
              yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjectDTO, stu, null);
              years.add(yearOfStudyDTO);

          }
          sps.add(new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
                  new ProfessorDTO(
                      sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
                      sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
                          new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
                                  sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
                  
                  years, new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(), null, null, null, null, null, null)));
      }
      return new ResponseEntity<Iterable<StudyProgrammeDTO>>(sps, HttpStatus.OK);
  }
    
    @PostMapping("/createStudyProgrammes")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StudyProgramme> create(@RequestBody StudyProgramme studyProgramme){
        try {
            service.save(studyProgramme);
            return new ResponseEntity<StudyProgramme>(studyProgramme, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<StudyProgramme>(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<StudyProgramme> update(@PathVariable("id") Long studyProgrammeId, @RequestBody StudyProgramme changedSp) {
        StudyProgramme sp = service.findOne(studyProgrammeId).orElse(null);
        if (sp != null) {
            service.save(changedSp);
            return new ResponseEntity<StudyProgramme>(changedSp, HttpStatus.OK);
        }
        return new ResponseEntity<StudyProgramme>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StudyProgramme> delete(@PathVariable("id") Long studyProgrammeId) {
        if (service.findOne(studyProgrammeId).isPresent()) {
            service.delete(studyProgrammeId);
            return new ResponseEntity<StudyProgramme>(HttpStatus.OK);
        }
        return new ResponseEntity<StudyProgramme>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/existsByNameInFaculty/{objectToChangeID}/{name}/{idFaculty}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByNameInFaculty(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("name") String name, @PathVariable("idFaculty") Long idFaculty) {
		if (service.existsByNameInFaculty(name, idFaculty) == true) {
			StudyProgramme objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getName().equals(name)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Study programme with this name already exists at this faculty!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Study programme with this name already exists at this faculty!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Study programme with this name don't exists at this faculty!"));
	}
}
//    @GetMapping("/getByUsername/{username}")
//	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
//	public ResponseEntity<?> getByUsername(@PathVariable("username") String username) {
//        ArrayList<StudyProgrammeDTO> sps = new ArrayList<StudyProgrammeDTO>();
//        YearOfStudyDTO yearOfStudyDTO = new YearOfStudyDTO();
////        FacultyDTO facultyDTO = new FacultyDTO();
//       
////        StudentOnTheYearDTO studentOnTheYearDTO = new StudentOnTheYearDTO();
//
//        Set<SubjectDTO> subjectDTO = new HashSet<>();
//
//        fservice.findAll();
//        for (StudyProgramme sp : service.findByUsername(username)) {
//            Set<RoleDTO> roleDTO = new HashSet<>();
//            if(sp.getDirector().getUser() != null) {
//                for(Role role: sp.getDirector().getUser().getRoles()) { 
//                    roleDTO.add(new RoleDTO(role.getId(), role.getName()));
//                }
//            }
//            else {
//                roleDTO = null;
//            }
//            Set<YearOfStudyDTO> years =new HashSet<YearOfStudyDTO>();
//            //Set<SubjectDTO> subjectDTO = new HashSet<>();
//            for(YearOfStudy year : sp.getYearOfStudy()){
//                Set<StudentOnTheYearDTO> stu =new HashSet<StudentOnTheYearDTO>();
////                Set<TopicDTO> topicDTO =new HashSet<TopicDTO>();
//                //Set<SubjectDTO> subjectDTO = new HashSet<>();
//
//
//
//                //subjectDTO = new SubjectDTO(year.getSubject().getId(),year.getSubject().getNaziv(),year.getSubject().getEspb(),year.getSubject().isObavezan(),
//                		//year.getSubject().getBrojPredavanja(),year.getSubject().getBrojVezbi(),year.getSubject().getDrugiObliciNastave(),year.getSubject().getIstrazivackiRad(),
//                		//year.getSubject().getOstaliCasovi(), null);
//                                    
//               /*for(StudentOnTheYear st: year.getStudentontheyear()) {
//                    studentOnTheYearDTO =new StudentOnTheYearDTO(st.getId(), st.getIndexNo(),st.getDateOfEntry(),
//                            new StudentDTO(st.getStudent().getId(),st.getStudent().getJmbg(),
//                                    st.getStudent().getDateOfBirth(),st.getStudent().getAddress(),st.getStudent().getPhoneNumber()));
//                    stu.add(studentOnTheYearDTO);
//                */
//                yearOfStudyDTO = new YearOfStudyDTO(year.getId(), year.getYear(), year.isActive(), subjectDTO, stu, null);
//                for(rs.ac.singidunum.novisad.isa.model.Subject s:year.getSubjects()) {
//                	subjectDTO.add(new SubjectDTO(s.getId(),s.getNaziv(),s.getEspb(),s.isObavezan(),s.getBrojPredavanja()
//                			,s.getBrojVezbi(),s.getDrugiObliciNastave(),s.getIstrazivackiRad(),s.getOstaliCasovi(),null, null, null));
//                }
//                years.add(yearOfStudyDTO);
//
//            }
//            sps.add(new StudyProgrammeDTO(sp.getId(), sp.getName(), sp.getDescription(),
//                    new ProfessorDTO(
//                        sp.getDirector().getId(), sp.getDirector().getJmbg(), sp.getDirector().getDateOfBirth(), 
//                        sp.getDirector().getAddress(),sp.getDirector().getPhoneNumber(), sp.getDirector().getBiography(),
//                            new UserDTO(sp.getDirector().getUser().getId(), sp.getDirector().getUser().getName(), sp.getDirector().getUser().getSurname(),
//                                    sp.getDirector().getUser().getUsername(), sp.getDirector().getUser().getEmail(), sp.getDirector().getUser().getPassword(), roleDTO)),
//                    
//                    years, new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(), null, null, null, null, null, null)));
//
//        }
//        
//        return new ResponseEntity<Iterable<StudyProgrammeDTO>>(sps, HttpStatus.OK);
//    }
//}
