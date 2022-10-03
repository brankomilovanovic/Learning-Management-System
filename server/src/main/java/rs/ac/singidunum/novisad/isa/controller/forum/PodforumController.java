package rs.ac.singidunum.novisad.isa.controller.forum;

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

import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.KorisnikNaForumuDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.ObjavaDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.PodforumDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.TemaDTO;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;
import rs.ac.singidunum.novisad.isa.model.forum.Objava;
import rs.ac.singidunum.novisad.isa.model.forum.Podforum;
import rs.ac.singidunum.novisad.isa.model.forum.Tema;
import rs.ac.singidunum.novisad.isa.service.forum.PodforumService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/podforum")
public class PodforumController {
	
	@Autowired
	private PodforumService service;
	
	@GetMapping
	public ResponseEntity<Iterable<PodforumDTO>> getAll() {
		ArrayList<PodforumDTO> podforumiDTO = new ArrayList<PodforumDTO>();
		PodforumDTO podforumDTO = new PodforumDTO();	
		for(Podforum podforum : service.findAll()) {
			int totalObjava = 0;
			Set<TemaDTO> temaDTO = new HashSet<>();
			for(Tema tema: podforum.getTema()) { 
				
				Set<ObjavaDTO> objavaDTO = new HashSet<>();
				for(Objava objava: tema.getObjava()) { 
					objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), null, null, null, null));
					totalObjava ++;
				}
				temaDTO.add(new TemaDTO(tema.getId(), tema.getNaziv(), tema.getPregleda(), null, 
						new KorisnikNaForumuDTO(tema.getAutor().getId(), tema.getAutor().getVremePrijavljivanja(), tema.getAutor().getObjave(), 
								new UserDTO(tema.getAutor().getUser().getId(), tema.getAutor().getUser().getName(), tema.getAutor().getUser().getSurname(), null, null, null, null)), objavaDTO));
			}
			
			podforumDTO = new PodforumDTO(podforum.getId(), podforum.getNaziv(), podforum.getOpis(), null, temaDTO);
			podforumDTO.setTotalTema(temaDTO.size());
			podforumDTO.setTotalObjava(totalObjava);
			podforumiDTO.add(podforumDTO);

		}
		return new ResponseEntity<Iterable<PodforumDTO>>(podforumiDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PodforumDTO> getOne(@PathVariable("id") Long id) {
		Optional<Podforum> podforum = service.findOne(id);
		PodforumDTO podforumDTO = new PodforumDTO();	
		if (podforum.isPresent()) { 
			
			Set<TemaDTO> temaDTO = new HashSet<>();
			for(Tema tema: podforum.get().getTema()) { 
				
				Set<ObjavaDTO> objavaDTO = new HashSet<>();
				for(Objava objava: tema.getObjava()) { 
					objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), objava.getSadrzaj(), null, null, null));
				}
				
				temaDTO.add(new TemaDTO(tema.getId(), tema.getNaziv(), tema.getPregleda(), null, 
						new KorisnikNaForumuDTO(tema.getAutor().getId(), tema.getAutor().getVremePrijavljivanja(), tema.getAutor().getObjave(), 
								new UserDTO(tema.getAutor().getUser().getId(), tema.getAutor().getUser().getName(), tema.getAutor().getUser().getSurname(), null, null, null, null)), objavaDTO));
			}
			
			podforumDTO = new PodforumDTO(podforum.get().getId(), podforum.get().getNaziv(), podforum.get().getOpis(), null, temaDTO);
			
			return new ResponseEntity<PodforumDTO>(podforumDTO, HttpStatus.OK);
		}
		return new ResponseEntity<PodforumDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Podforum> create(@RequestBody Podforum object){
	    try {
	    	service.save(object);
			return new ResponseEntity<Podforum>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<Podforum>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Podforum> update(@PathVariable("id") Long id, @RequestBody Podforum changedObject) {
		Podforum objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<Podforum>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<Podforum>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Podforum> delete(@PathVariable("id") Long id) {
		Podforum podforum = service.findOne(id).orElse(null);
		if (service.findOne(id).isPresent()) {
			for (KorisnikNaForumu korisnikNaForumu : podforum.getKorisnici_follow_podforum()) {
				korisnikNaForumu.getFollow_podforum().remove(podforum);
			}
			service.delete(id);
			return new ResponseEntity<Podforum>(HttpStatus.OK);
		}
		return new ResponseEntity<Podforum>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existByNaziv/{objectToChangeID}/{naziv}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existByNaziv(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("naziv") String naziv) {
		if (service.exsistByNaziv(naziv) == true) {
			Podforum objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getNaziv().equals(naziv)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Subforum with this name already exists!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Subforum with this name already exists!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Free!"));
	}
}
