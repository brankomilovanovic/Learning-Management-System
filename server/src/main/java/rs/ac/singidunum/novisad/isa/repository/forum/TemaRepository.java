package rs.ac.singidunum.novisad.isa.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.singidunum.novisad.isa.model.forum.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	@Modifying
    @Transactional
	@Query(value = "UPDATE forum_tema SET pregleda = pregleda + 1 WHERE forum_tema.id = :id", nativeQuery = true)
	void dodajPregledTemi(@Param("id") Long id);
	
	@Query(value = "SELECT forum_tema.*"
			+ " FROM forum_podforum"
			+ " INNER JOIN forum_tema ON forum_podforum.id = forum_tema.podforum_id"
			+ " INNER JOIN forum_objava AS p ON p.id ="
			+ " ("
			+ "	   SELECT id"
			+ "    FROM forum_objava"
			+ "    WHERE forum_objava.tema_id = forum_tema.id"
			+ "    ORDER BY forum_objava.vreme_postavljanja DESC LIMIT 1"
			+ " )"
			+ " WHERE forum_podforum.id = :id ORDER BY vreme_postavljanja DESC", nativeQuery = true)
	Iterable<Tema> findAllByPodforumId(@Param("id") Long id);
}
