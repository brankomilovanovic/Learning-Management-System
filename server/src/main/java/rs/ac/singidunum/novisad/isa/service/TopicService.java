package rs.ac.singidunum.novisad.isa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.novisad.isa.model.Topic;
import rs.ac.singidunum.novisad.isa.repository.TopicRepository;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
	
	public Iterable<Topic> findAll() {
		return topicRepository.findAll();
	}
	
	public Optional<Topic> findOne(Long id) {
		return topicRepository.findById(id);
	}
	
	public Topic save(Topic topic) {
		return topicRepository.save(topic);
	}
	
	public void delete(Long id) {
		topicRepository.deleteById(id);
	}
	
	public void delete(Topic topic) {
		topicRepository.delete(topic);
	}
	
	public boolean existsByOpisWithTopicType(String opis, Long topicTypeId) {
		return topicRepository.existsByOpisWithTopicType(opis, topicTypeId);
	}
}
