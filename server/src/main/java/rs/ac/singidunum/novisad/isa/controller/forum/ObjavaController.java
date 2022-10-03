package rs.ac.singidunum.novisad.isa.controller.forum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.KorisnikNaForumuDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.ObjavaDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.PodforumDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.TemaDTO;
import rs.ac.singidunum.novisad.isa.model.File;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.forum.Objava;
import rs.ac.singidunum.novisad.isa.service.forum.ObjavaService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/objava")
public class ObjavaController {

	@Autowired
	private ObjavaService service;
	
	@GetMapping
	public ResponseEntity<Iterable<ObjavaDTO>> getAll() {
		ArrayList<ObjavaDTO> objavaDTO = new ArrayList<ObjavaDTO>();
		for(Objava objava : service.findAll()) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), objava.getSadrzaj(), null, 
					prilozi, 
					new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
							new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, null))));
		}
		return new ResponseEntity<Iterable<ObjavaDTO>>(objavaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ObjavaDTO> getOne(@PathVariable("id") Long id) {
		Optional<Objava> objava = service.findOne(id);
		ObjavaDTO objavaDTO = new ObjavaDTO();	
		if (objava.isPresent()) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.get().getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			objavaDTO = new ObjavaDTO(objava.get().getId(), objava.get().getVremePostavljanja(), objava.get().getSadrzaj(), null, 
					prilozi, 
					new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
							new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)));
						
			return new ResponseEntity<ObjavaDTO>(objavaDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ObjavaDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getLastPostForSubforum/{podforum_id}")
	public ResponseEntity<ObjavaDTO> getLastPostForSubforum(@PathVariable("podforum_id") Long podforum_id) {
		Optional<Objava> objava = service.findLastPostForSubforum(podforum_id);
		ObjavaDTO objavaDTO = new ObjavaDTO();	
		if (objava.isPresent()) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.get().getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			objavaDTO = new ObjavaDTO(objava.get().getId(), objava.get().getVremePostavljanja(), objava.get().getSadrzaj(), 
					new TemaDTO(objava.get().getTema().getId(), objava.get().getTema().getNaziv(), objava.get().getTema().getPregleda(), new PodforumDTO(objava.get().getTema().getPodforum().getId(), null, null, null, null), 
							new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
									new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)), null), 
					prilozi, 
					new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
							new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)));
						
			return new ResponseEntity<ObjavaDTO>(objavaDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ObjavaDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getAllForTema/{topic_id}")
	public ResponseEntity<Iterable<ObjavaDTO>> getAllForTema(@PathVariable("topic_id") Long topic_id) {
		ArrayList<ObjavaDTO> objavaDTO = new ArrayList<ObjavaDTO>();
		for(Objava objava : service.findAllForTema(topic_id)) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			Set<RoleDTO> roles = new HashSet<>();
			for(Role role: objava.getAutor().getUser().getRoles()) { 
				roles.add(new RoleDTO(role.getId(), role.getName()));
			}
						
			objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), objava.getSadrzaj(), 
					new TemaDTO(objava.getTema().getId(), objava.getTema().getNaziv(), objava.getTema().getPregleda(), new PodforumDTO(objava.getTema().getPodforum().getId(), null, null, null, null), 
							new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
									new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, roles)), null), 
					prilozi, 
					new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
							new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, null))));
						
		}
		return new ResponseEntity<Iterable<ObjavaDTO>>(objavaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getLastPostForTopic/{topic_id}")
	public ResponseEntity<ObjavaDTO> getLastPostForTopic(@PathVariable("topic_id") Long topic_id) {
		Optional<Objava> objava = service.findLastPostForTopic(topic_id);
		ObjavaDTO objavaDTO = new ObjavaDTO();	
		if (objava.isPresent()) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.get().getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			objavaDTO = new ObjavaDTO(objava.get().getId(), objava.get().getVremePostavljanja(), objava.get().getSadrzaj(), 
					new TemaDTO(objava.get().getTema().getId(), objava.get().getTema().getNaziv(), objava.get().getTema().getPregleda(), new PodforumDTO(objava.get().getTema().getPodforum().getId(), null, null, null, null), 
							new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
									new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)), null), 
					prilozi, 
					new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
							new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)));
						
			return new ResponseEntity<ObjavaDTO>(objavaDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ObjavaDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getFirstObjavaForTopic/{podforum_id}")
	public ResponseEntity<ObjavaDTO> getFirstObjavaForTopic(@PathVariable("podforum_id") Long podforum_id) {
		Optional<Objava> objava = service.findFirstObjavaForTopic(podforum_id);
		ObjavaDTO objavaDTO = new ObjavaDTO();	
		if (objava.isPresent()) {
			
			Set<FileDTO> prilozi = new HashSet<>();
			for(File file : objava.get().getPrilozi()) { 
				prilozi.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
			}
			
			objavaDTO = new ObjavaDTO(objava.get().getId(), objava.get().getVremePostavljanja(), objava.get().getSadrzaj(), 
					new TemaDTO(objava.get().getTema().getId(), objava.get().getTema().getNaziv(), objava.get().getTema().getPregleda(), new PodforumDTO(objava.get().getTema().getPodforum().getId(), null, null, null, null), 
					new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
							new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)), null), 
					prilozi, 
					new KorisnikNaForumuDTO(objava.get().getAutor().getId(), objava.get().getAutor().getVremePrijavljivanja(), objava.get().getAutor().getObjave(), 
							new UserDTO(objava.get().getAutor().getUser().getId(), objava.get().getAutor().getUser().getName(), objava.get().getAutor().getUser().getSurname(), null, null, null, null)));
						
			return new ResponseEntity<ObjavaDTO>(objavaDTO, HttpStatus.OK);
		}
		return new ResponseEntity<ObjavaDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Objava> create(@RequestBody Objava object){
	    try {
	    	service.save(object);
			return new ResponseEntity<Objava>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<Objava>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Objava> update(@PathVariable("id") Long id, @RequestBody Objava changedObject) {
		Objava objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<Objava>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<Objava>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Objava> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<Objava>(HttpStatus.OK);
		}
		return new ResponseEntity<Objava>(HttpStatus.NOT_FOUND);
	}
}
