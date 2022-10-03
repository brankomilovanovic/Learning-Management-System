package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM topic"
			+ " WHERE topic.opis = :opis AND topic.topic_type_id = :topicId", nativeQuery = true)
	Boolean existsByOpisWithTopicType(@Param("opis") String opis, @Param("topicId") Long topicId);
	
}
