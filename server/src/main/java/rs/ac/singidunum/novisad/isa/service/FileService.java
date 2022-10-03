package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.File;
import rs.ac.singidunum.novisad.isa.repository.FileRepository;

@Service
public class FileService {
	
	@Autowired
	private FileRepository repository;
	
	public Iterable<File> findAll() {
		return repository.findAll();
	}
	
	public Optional<File> findOne(Long id) {
		return repository.findById(id);
	}
	
	public boolean existFileByURL(String URL) {
		return repository.existFileByURL(URL);
	}
	
	public File save(File object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(File object) {
		repository.delete(object);
	}

}
