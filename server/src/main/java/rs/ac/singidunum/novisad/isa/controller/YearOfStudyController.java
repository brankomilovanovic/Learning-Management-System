package rs.ac.singidunum.novisad.isa.controller;

import java.sql.Date;
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

import rs.ac.singidunum.novisad.isa.dto.StudentOnTheYearDTO;
import rs.ac.singidunum.novisad.isa.dto.StudyProgrammeDTO;
import rs.ac.singidunum.novisad.isa.dto.SubjectDTO;
import rs.ac.singidunum.novisad.isa.dto.TopicDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeOfTopicDTO;
import rs.ac.singidunum.novisad.isa.dto.YearOfStudyDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Subject;
import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.model.YearOfStudy;
import rs.ac.singidunum.novisad.isa.service.YearOfStudyService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/godinastudija")
public class YearOfStudyController {
	
	@Autowired
	private YearOfStudyService ys;
	
//	@Autowired
//	private TopicService t;
//	@Autowired
//	private SubjectService st;
	
	@GetMapping("/svegodine")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<YearOfStudyDTO>> dobavi() {
		ArrayList<YearOfStudyDTO> gosti = new ArrayList<YearOfStudyDTO>();
		
		for(YearOfStudy p : ys.findAll()) 
		{
			Set<StudentOnTheYearDTO> year =new HashSet<StudentOnTheYearDTO>();			
			Set<TopicDTO> topicsDTO = new HashSet<>();
			
			Set<SubjectDTO> subjectDTO = new HashSet<>();
			for(Subject subject:p.getSubjects()) {
				subjectDTO.add(new SubjectDTO(subject.getId(),subject.getNaziv(),subject.getEspb(),
						subject.isObavezan(),subject.getBrojPredavanja(),subject.getBrojVezbi(),subject.getDrugiObliciNastave()
						,subject.getIstrazivackiRad(),subject.getOstaliCasovi(),subject.getSilabus(), topicsDTO, null));
				
				for(Topic topic: subject.getTopic()) { 
					topicsDTO.add(new TopicDTO(topic.getId(), topic.getOpis(), new TypeOfTopicDTO(topic.getTopicType().getId(), topic.getTopicType().getNaziv())));
				}
			}
				
				
			/*for(StudentOnTheYear st: p.getStudentontheyear()) {
				studentOnTheYearDTO =new StudentOnTheYearDTO(st.getId(), st.getIndexNo(),st.getDateOfEntry(),
						new StudentDTO(st.getStudent().getId(),st.getStudent().getJmbg(),
								st.getStudent().getDateOfBirth(),st.getStudent().getAddress(),st.getStudent().getPhoneNumber()));
				year.add(studentOnTheYearDTO);
			}*/
			StudyProgrammeDTO studyProgrammeDTO=new StudyProgrammeDTO();
			studyProgrammeDTO =new StudyProgrammeDTO(p.getStudyProgramme().getId(), p.getStudyProgramme().getName(), null, null);

			
			gosti.add(new YearOfStudyDTO(p.getId(),p.getYear(), p.isActive(), subjectDTO, year, studyProgrammeDTO));
		}
		
		return new ResponseEntity<Iterable<YearOfStudyDTO>>(gosti, HttpStatus.OK);
	}
	
	@PostMapping("/createyear")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<YearOfStudy> create(@RequestBody YearOfStudy yearOfStudy){
	    try {
	    	ys.save(yearOfStudy);
			return new ResponseEntity<YearOfStudy>(yearOfStudy, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<YearOfStudy>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<YearOfStudy> update(@PathVariable("id") Long yearid, @RequestBody YearOfStudy changedYearOfStudy) {
		YearOfStudy yae = ys.findOne(yearid).orElse(null);
		if (yae != null) {
			ys.save(changedYearOfStudy);
			return new ResponseEntity<YearOfStudy>(changedYearOfStudy, HttpStatus.OK);
		}
		return new ResponseEntity<YearOfStudy>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<YearOfStudy> deleteKupovina(@PathVariable("id") Long YearOfStudyid) {
		if (ys.findOne(YearOfStudyid).isPresent()) {
			ys.delete(YearOfStudyid);
			return new ResponseEntity<YearOfStudy>(HttpStatus.OK);
		}
		return new ResponseEntity<YearOfStudy>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existsByStudyProgrammeAndDate/{objectToChangeID}/{id}/{date}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existsByStudyProgrammeAndDate(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("id") Long id, @PathVariable("date") Date date) {
		if (ys.existsByStudyProgrammeAndDate(id, date) == true) {
			YearOfStudy objectToChange = ys.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getStudyProgramme().getId().equals(id)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: There is already a study program for that date!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: There is already a study program for that date!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
