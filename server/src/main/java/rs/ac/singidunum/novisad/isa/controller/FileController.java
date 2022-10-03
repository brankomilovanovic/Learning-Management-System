package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
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

import rs.ac.singidunum.novisad.isa.dto.FileDTO;
import rs.ac.singidunum.novisad.isa.model.File;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.service.FileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/files")
public class FileController {
	
	@Autowired
	private FileService service;
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<FileDTO>> getAll() {
		ArrayList<FileDTO> files = new ArrayList<FileDTO>();
		for(File file : service.findAll()) {
			files.add(new FileDTO(file.getId(), file.getOpis(), file.getUrl()));
		}
		return new ResponseEntity<Iterable<FileDTO>>(files, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<File> create(@RequestBody File object){
	    try {
	    	service.save(object);
			return new ResponseEntity<File>(object, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<File>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<File> update(@PathVariable("id") Long id, @RequestBody File changedObject) {
		File objectExist = service.findOne(id).orElse(null);
		if (objectExist != null) {
			service.save(changedObject);
			return new ResponseEntity<File>(changedObject, HttpStatus.OK);
		}
		return new ResponseEntity<File>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<File> delete(@PathVariable("id") Long id) {
		if (service.findOne(id).isPresent()) {
			service.delete(id);
			return new ResponseEntity<File>(HttpStatus.OK);
		}
		return new ResponseEntity<File>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/existFileByURL/{objectToChangeID}/{URL}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> existFileByURL(@PathVariable("objectToChangeID") Long objectToChangeID, @PathVariable("URL") String URL) {
		if (service.existFileByURL(URL) == true) {
			File objectToChange = service.findOne(objectToChangeID).orElse(null);
			if(objectToChange != null) {
				if(!objectToChange.getUrl().equals(URL)) {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: File with this url already exists!"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: File with this url already exists!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("File with this url don't exists!"));
	}
}
