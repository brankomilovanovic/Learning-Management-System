package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;
import rs.ac.singidunum.novisad.isa.repository.TypeOfTeachingRepository;

@Service
public class TypeOfTeachingService {
	
	@Autowired
	private TypeOfTeachingRepository typeOfTeachingRepository;
	
	public Iterable<TypeOfTeaching> findAll() {
		return typeOfTeachingRepository.findAll();
	}
	
	public Optional<TypeOfTeaching> findOne(Long id) {
		return typeOfTeachingRepository.findById(id);
	}
	
	public TypeOfTeaching save(TypeOfTeaching typeOfTeaching) {
		return typeOfTeachingRepository.save(typeOfTeaching);
	}
	
	public void delete(Long id) {
		typeOfTeachingRepository.deleteById(id);
	}
	
	public void delete(TypeOfTeaching typeOfTeaching) {
		typeOfTeachingRepository.delete(typeOfTeaching);
	}

}
