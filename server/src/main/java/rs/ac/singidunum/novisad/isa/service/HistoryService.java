package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.StudentHistory;
import rs.ac.singidunum.novisad.isa.repository.HistoryRepository;

@Service
public class HistoryService {
	
	@Autowired
	private HistoryRepository h;
	
	public Iterable<StudentHistory> findAll() {
		return h.findAll();
	}
	
	public Optional<StudentHistory> findOne(Long id) {
		return h.findById(id);
	}

	public StudentHistory save(StudentHistory professor) {
		return h.save(professor);
	}
	
	public void delete(StudentHistory professor) {
		h.delete(professor);
	}
	
	public void delete(Long id) {
		h.deleteById(id);
	}
	
}
