package rs.ac.singidunum.novisad.isa.controller.forum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.forum.ForumDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.PodforumDTO;
import rs.ac.singidunum.novisad.isa.model.forum.Forum;
import rs.ac.singidunum.novisad.isa.model.forum.Podforum;
import rs.ac.singidunum.novisad.isa.service.forum.ForumService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/forum")
public class ForumController {
	
	@Autowired
	private ForumService service;
	
	@GetMapping
	public ResponseEntity<Iterable<ForumDTO>> getAll() {
		ArrayList<ForumDTO> forumDTO = new ArrayList<ForumDTO>();
		for(Forum forum : service.findAll()) {

			Set<PodforumDTO> podforumDTO = new HashSet<>();
			for(Podforum podforum: forum.getPodforum()) { 
				podforumDTO.add(new PodforumDTO(podforum.getId(), podforum.getNaziv(), podforum.getOpis(), null, null));
			}
			
			forumDTO.add(new ForumDTO(forum.getId(), forum.isJavni(), podforumDTO));
		}
		return new ResponseEntity<Iterable<ForumDTO>>(forumDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getForum")
	public ResponseEntity<ForumDTO> getForum() {
		Optional<Forum> forum = service.findForum();
		ForumDTO forumDTO = new ForumDTO();
		if(forum.isPresent()) {

			Set<PodforumDTO> podforumDTO = new HashSet<>();
			for(Podforum podforum: forum.get().getPodforum()) { 
				podforumDTO.add(new PodforumDTO(podforum.getId(), podforum.getNaziv(), podforum.getOpis(), null, null));
			}
			
			forumDTO = new ForumDTO(forum.get().getId(), forum.get().isJavni(), podforumDTO);
		}
		return new ResponseEntity<ForumDTO>(forumDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getOne")
	public ResponseEntity<ForumDTO> getOne(@RequestParam(name = "id") String id) {
		Optional<Forum> forum = service.findOne(Long.parseLong(id));
        ForumDTO forumDTO = new ForumDTO();
      	if(forum.isPresent()) { 
			Set<PodforumDTO> podforumDTO = new HashSet<>();
			for(Podforum podforum: forum.get().getPodforum()) { 
				podforumDTO.add(new PodforumDTO(podforum.getId(), podforum.getNaziv(), podforum.getOpis(), null, null));
			}
			
			forumDTO = new ForumDTO(forum.get().getId(), forum.get().isJavni(), podforumDTO);
		}
		return new ResponseEntity<ForumDTO>(forumDTO, HttpStatus.OK);
	}
	
	@GetMapping("/updateVidljivost/{vidljivost}")
	public void updateVidljivost(@PathVariable("vidljivost") boolean vidljivost) {
		service.updateVidljivost(vidljivost);
	}

}
