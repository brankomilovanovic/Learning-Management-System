package rs.ac.singidunum.novisad.isa.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.forum.Podforum;

@Repository
public interface PodforumRepository extends JpaRepository<Podforum, Long> {
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM forum_podforum WHERE naziv = :naziv", nativeQuery = true)
	Boolean exsistByName(@Param("naziv") String naziv);
}
