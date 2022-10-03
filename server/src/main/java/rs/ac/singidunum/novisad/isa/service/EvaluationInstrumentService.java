package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.EvaluationInstrument;
import rs.ac.singidunum.novisad.isa.repository.EvaluationInstrumentRepository;

@Service
public class EvaluationInstrumentService {
	
	@Autowired
	private EvaluationInstrumentRepository repository;
	
	public Iterable<EvaluationInstrument> findAll() {
		return repository.findAll();
	}
	
	public Optional<EvaluationInstrument> findOne(Long id) {
		return repository.findById(id);
	}
	
	public boolean existsByFileID(Long id) {
		return repository.existsByFileID(id);
	}
	
	public boolean existsByTipTestiranja(String tipTestiranja) {
		return repository.existsByTipTestiranja(tipTestiranja);
	}
	
	public EvaluationInstrument save(EvaluationInstrument object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(EvaluationInstrument object) {
		repository.delete(object);
	}
}
