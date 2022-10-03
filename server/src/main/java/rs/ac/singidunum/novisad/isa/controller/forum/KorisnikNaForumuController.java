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

import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.KorisnikNaForumuDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.PodforumDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.TemaDTO;
import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;
import rs.ac.singidunum.novisad.isa.model.forum.Podforum;
import rs.ac.singidunum.novisad.isa.model.forum.Tema;
import rs.ac.singidunum.novisad.isa.service.forum.KorisnikNaForumuService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/korisnik_na_forumu")
public class KorisnikNaForumuController {
	
	@Autowired
	private KorisnikNaForumuService service;
	
	@GetMapping
	public ResponseEntity<Iterable<KorisnikNaForumuDTO>> getAll() {
		ArrayList<KorisnikNaForumuDTO> korisniciNaForumuDTO = new ArrayList<KorisnikNaForumuDTO>();
		for(KorisnikNaForumu korisnik : service.findAll()) {
			
			korisniciNaForumuDTO.add(new KorisnikNaForumuDTO(korisnik.getId(), korisnik.getVremePrijavljivanja(), korisnik.getObjave(), 
					new UserDTO(korisnik.getUser().getId(), korisnik.getUser().getName(), korisnik.getUser().getSurname(), null, null, null, null)));
		}
		return new ResponseEntity<Iterable<KorisnikNaForumuDTO>>(korisniciNaForumuDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<KorisnikNaForumuDTO> getOne(@PathVariable("id") Long id) {
		Optional<KorisnikNaForumu> korisnik = service.findOne(id);
		KorisnikNaForumuDTO korisnikNaForumuDTO = new KorisnikNaForumuDTO();	
		if (korisnik.isPresent()) { 

			korisnikNaForumuDTO = new KorisnikNaForumuDTO(korisnik.get().getId(), korisnik.get().getVremePrijavljivanja(), korisnik.get().getObjave(), 
					new UserDTO(korisnik.get().getUser().getId(), korisnik.get().getUser().getName(), korisnik.get().getUser().getSurname(), null, null, null, null));
					
			return new ResponseEntity<KorisnikNaForumuDTO>(korisnikNaForumuDTO, HttpStatus.OK);
		}
		return new ResponseEntity<KorisnikNaForumuDTO>(korisnikNaForumuDTO, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getByUsername/{username}")
	public ResponseEntity<KorisnikNaForumuDTO> getByUsername(@PathVariable("username") String username) {
		Optional<KorisnikNaForumu> korisnik = service.findByUsername(username);
		KorisnikNaForumuDTO korisnikNaForumuDTO = new KorisnikNaForumuDTO();	
		if (korisnik.isPresent()) { 

			korisnikNaForumuDTO = new KorisnikNaForumuDTO(korisnik.get().getId(), korisnik.get().getVremePrijavljivanja(), korisnik.get().getObjave(), 
					new UserDTO(korisnik.get().getUser().getId(), korisnik.get().getUser().getName(), korisnik.get().getUser().getSurname(), null, null, null, null));
			
			Set<TemaDTO> teme = new HashSet<>();
			for(Tema tema: korisnik.get().getFollow_teme()) { 
				teme.add(new TemaDTO(tema.getId(), tema.getNaziv(), tema.getPregleda(), null, null, null));
			}
			Set<PodforumDTO> podforumi = new HashSet<>();
			for(Podforum podforum: korisnik.get().getFollow_podforum()) { 
				podforumi.add(new PodforumDTO(podforum.getId(), podforum.getNaziv(), podforum.getOpis(), null, null));
			}
			
			korisnikNaForumuDTO.setFollow_teme(teme);
			korisnikNaForumuDTO.setFollow_podforum(podforumi);
					
			return new ResponseEntity<KorisnikNaForumuDTO>(korisnikNaForumuDTO, HttpStatus.OK);
		}
		return new ResponseEntity<KorisnikNaForumuDTO>(korisnikNaForumuDTO, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<KorisnikNaForumu> create(@RequestBody KorisnikNaForumu object){
	    try {
	    	service.save(object);
			return new ResponseEntity<KorisnikNaForumu>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<KorisnikNaForumu>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<KorisnikNaForumu> update(@PathVariable("id") Long id, @RequestBody KorisnikNaForumu changedObject) {
		KorisnikNaForumu objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<KorisnikNaForumu>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<KorisnikNaForumu>(HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<KorisnikNaForumu> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<KorisnikNaForumu>(HttpStatus.OK);
		}
		return new ResponseEntity<KorisnikNaForumu>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/getFollowTema/{id}/{korisnik_id}")
	public boolean getFollowTemaExists(@PathVariable("id") Long id, @PathVariable("korisnik_id") Long korisnik_id) {
		if (service.getFollowTemaExists(id, korisnik_id) == true) {
			return true;
		} 
		return false;
	}
	
	@GetMapping("/getFollowPodforum/{id}/{korisnik_id}")
	public boolean getFollowPodforumExists(@PathVariable("id") Long id, @PathVariable("korisnik_id") Long korisnik_id) {
		if (service.getFollowPodforumExists(id, korisnik_id) == true) {
			return true;
		} 
		return false;
	}
	
	@GetMapping("/getAllFollowTema/{tema_id}")
	public ResponseEntity<Iterable<KorisnikNaForumuDTO>> getAllFollowTema(@PathVariable("tema_id") Long tema_id) {
		ArrayList<KorisnikNaForumuDTO> korisnici = new ArrayList<KorisnikNaForumuDTO>();
		for(KorisnikNaForumu korisnik : service.findAllFollowTemu(tema_id)) {
			korisnici.add(new KorisnikNaForumuDTO(korisnik.getId(), korisnik.getVremePrijavljivanja(), korisnik.getObjave(), 
					new UserDTO(korisnik.getUser().getId(), korisnik.getUser().getName(), korisnik.getUser().getSurname(), null, korisnik.getUser().getEmail(), null, null)));
		}
		return new ResponseEntity<Iterable<KorisnikNaForumuDTO>>(korisnici, HttpStatus.OK);
	}
	
	@GetMapping("/getAllFollowPodforum/{podforum_id}")
	public ResponseEntity<Iterable<KorisnikNaForumuDTO>> getAllFollowPodforum(@PathVariable("podforum_id") Long podforum_id) {
		ArrayList<KorisnikNaForumuDTO> korisnici = new ArrayList<KorisnikNaForumuDTO>();
		for(KorisnikNaForumu korisnik : service.findAllFollowPodforum(podforum_id)) {
			korisnici.add(new KorisnikNaForumuDTO(korisnik.getId(), korisnik.getVremePrijavljivanja(), korisnik.getObjave(), 
					new UserDTO(korisnik.getUser().getId(), korisnik.getUser().getName(), korisnik.getUser().getSurname(), null, korisnik.getUser().getEmail(), null, null)));
		}
		
		return new ResponseEntity<Iterable<KorisnikNaForumuDTO>>(korisnici, HttpStatus.OK);
	}

}
