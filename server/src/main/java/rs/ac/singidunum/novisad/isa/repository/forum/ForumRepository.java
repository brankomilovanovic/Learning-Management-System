package rs.ac.singidunum.novisad.isa.repository.forum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.singidunum.novisad.isa.model.forum.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
	
	@Query(value = "SELECT * FROM forum WHERE forum.id = 1", nativeQuery = true)
	Optional<Forum> findForum();
	
	@Modifying
    @Transactional
	@Query(value = "UPDATE forum SET javni = :vidljivost WHERE id = 1", nativeQuery = true)
	void updateVidljivost(boolean vidljivost);

}
