package rs.ac.singidunum.novisad.isa.repository.forum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.forum.Objava;

@Repository
public interface ObjavaRepository extends JpaRepository<Objava, Long> {

	@Query(value = "SELECT forum_objava.*"
			+ " FROM forum_podforum"
			+ " INNER JOIN forum_tema ON forum_podforum.id = forum_tema.podforum_id"
			+ " INNER JOIN forum_objava ON forum_tema.id = forum_objava.tema_id"
			+ " WHERE forum_podforum.id = :id ORDER BY forum_objava.vreme_postavljanja DESC LIMIT 1", nativeQuery = true)
	Optional<Objava> findLastPostForSubforum(@Param("id") Long id);
	
	@Query(value = "SELECT forum_objava.*"
			+ " FROM forum_tema"
			+ " INNER JOIN forum_objava ON forum_tema.id = forum_objava.tema_id"
			+ " WHERE forum_tema.id = :id ORDER BY forum_objava.vreme_postavljanja DESC LIMIT 1", nativeQuery = true)
	Optional<Objava> findLastPostForTopic(@Param("id") Long id);
	
	@Query(value = "SELECT forum_objava.*"
			+ " FROM forum_tema"
			+ " INNER JOIN forum_objava ON forum_tema.id = forum_objava.tema_id"
			+ " WHERE forum_tema.id = :id ORDER BY forum_objava.id ASC LIMIT 1", nativeQuery = true)
	Optional<Objava> findFirstObjavaForTopic(@Param("id") Long id);
	
	@Query(value = "SELECT forum_objava.*"
			+ " FROM forum_tema"
			+ " INNER JOIN forum_objava ON forum_tema.id = forum_objava.tema_id"
			+ " WHERE forum_tema.id = :id ORDER BY forum_objava.vreme_postavljanja ASC", nativeQuery = true)
	Iterable<Objava> findAllForTema(@Param("id") Long id);
}
