package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TypeRanks;
import rs.ac.singidunum.novisad.isa.repository.TypeRanksRepository;

@Service
public class TypeRanksService {
	
	@Autowired
	private TypeRanksRepository typeRanksRepository;
	
	public Iterable<TypeRanks> findAll() {
		return typeRanksRepository.findAll();
	}
	
	public Optional<TypeRanks> findOne(Long id) {
		return typeRanksRepository.findById(id);
	}
	
	public boolean existByName(String name) {
		return typeRanksRepository.existByName(name);
	}
	
	public TypeRanks save(TypeRanks typeRanks) {
		return typeRanksRepository.save(typeRanks);
	}
	
	public void delete(Long id) {
		typeRanksRepository.deleteById(id);
	}
	
	public void delete(TypeRanks typeRanks) {
		typeRanksRepository.delete(typeRanks);
	}
	
}
