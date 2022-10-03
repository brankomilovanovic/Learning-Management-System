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
import rs.ac.singidunum.novisad.isa.dto.forum.ObjavaDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.PodforumDTO;
import rs.ac.singidunum.novisad.isa.dto.forum.TemaDTO;
import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;
import rs.ac.singidunum.novisad.isa.model.forum.Objava;
import rs.ac.singidunum.novisad.isa.model.forum.Tema;
import rs.ac.singidunum.novisad.isa.service.forum.TemaService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/tema")
public class TemaController {
	
	@Autowired
	private TemaService service;
	
	@GetMapping
	public ResponseEntity<Iterable<TemaDTO>> getAll() {
		ArrayList<TemaDTO> temaDTO = new ArrayList<TemaDTO>();
		for(Tema tema : service.findAll()) {
			
			Set<ObjavaDTO> objavaDTO = new HashSet<ObjavaDTO>();
			for(Objava objava : tema.getObjava()) {
				objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), null, null, null, 
						new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
								new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, null))));
			}
			
			temaDTO.add(new TemaDTO(tema.getId(), tema.getNaziv(), tema.getPregleda(), null, 
					new KorisnikNaForumuDTO(tema.getAutor().getId(), tema.getAutor().getVremePrijavljivanja(), tema.getAutor().getObjave(), 
							new UserDTO(tema.getAutor().getUser().getId(), tema.getAutor().getUser().getName(), tema.getAutor().getUser().getSurname(), null, null, null, null)), objavaDTO));
		}
		return new ResponseEntity<Iterable<TemaDTO>>(temaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getAllByPodforumId/{id}")
	public ResponseEntity<Iterable<TemaDTO>> getAllByPodforumId(@PathVariable("id") Long id) {
		ArrayList<TemaDTO> temaDTO = new ArrayList<TemaDTO>();
		for(Tema tema : service.findAllByPodforumId(id)) {
			
			Set<ObjavaDTO> objavaDTO = new HashSet<ObjavaDTO>();
			for(Objava objava : tema.getObjava()) {
				objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), null, null, null, 
						new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
								new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, null))));
			}
			
			temaDTO.add(new TemaDTO(tema.getId(), tema.getNaziv(), tema.getPregleda(), 
					null, 
					new KorisnikNaForumuDTO(tema.getAutor().getId(), tema.getAutor().getVremePrijavljivanja(), tema.getAutor().getObjave(), 
							new UserDTO(tema.getAutor().getUser().getId(), tema.getAutor().getUser().getName(), tema.getAutor().getUser().getSurname(), null, null, null, null)), objavaDTO));
		}
		return new ResponseEntity<Iterable<TemaDTO>>(temaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TemaDTO> getOne(@PathVariable("id") Long id) {
		Optional<Tema> tema = service.findOne(id);
		TemaDTO temaDTO = new TemaDTO();	
		if (tema.isPresent()) { 
			
			Set<ObjavaDTO> objavaDTO = new HashSet<ObjavaDTO>();
			for(Objava objava : tema.get().getObjava()) {
				objavaDTO.add(new ObjavaDTO(objava.getId(), objava.getVremePostavljanja(), objava.getSadrzaj(), null, null, 
						new KorisnikNaForumuDTO(objava.getAutor().getId(), objava.getAutor().getVremePrijavljivanja(), objava.getAutor().getObjave(), 
								new UserDTO(objava.getAutor().getUser().getId(), objava.getAutor().getUser().getName(), objava.getAutor().getUser().getSurname(), null, null, null, null))));
			}
			
			temaDTO = new TemaDTO(tema.get().getId(), tema.get().getNaziv(), tema.get().getPregleda(), 
					new PodforumDTO(tema.get().getPodforum().getId(), tema.get().getPodforum().getNaziv(), null, null, null), 
					new KorisnikNaForumuDTO(tema.get().getAutor().getId(), tema.get().getAutor().getVremePrijavljivanja(), tema.get().getAutor().getObjave(), 
							new UserDTO(tema.get().getAutor().getUser().getId(), tema.get().getAutor().getUser().getName(), tema.get().getAutor().getUser().getSurname(), null, null, null, null)), objavaDTO);
					
			return new ResponseEntity<TemaDTO>(temaDTO, HttpStatus.OK);
		}
		return new ResponseEntity<TemaDTO>(temaDTO, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Tema> create(@RequestBody Tema object){
	    try {
	    	service.save(object);
			return new ResponseEntity<Tema>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<Tema>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tema> update(@PathVariable("id") Long id, @RequestBody Tema changedObject) {
		Tema objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<Tema>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<Tema>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/dodajPregledTemi/{id}")
	public void dodajPregledTemi(@PathVariable("id") Long id) {
		service.dodajPregledTemi(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Tema> delete(@PathVariable("id") Long id) {
		Tema tema = service.findOne(id).orElse(null);
		if (tema != null) {
			for (KorisnikNaForumu korisnikNaForumu : tema.getKorisnici_follow_tema()) {
				korisnikNaForumu.getFollow_teme().remove(tema);
			}
			service.delete(id);
			return new ResponseEntity<Tema>(HttpStatus.OK);
		}
		return new ResponseEntity<Tema>(HttpStatus.NOT_FOUND);
	}
}
