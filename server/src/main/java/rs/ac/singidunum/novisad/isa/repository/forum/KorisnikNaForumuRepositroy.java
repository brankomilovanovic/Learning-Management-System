package rs.ac.singidunum.novisad.isa.repository.forum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.forum.KorisnikNaForumu;

@Repository
public interface KorisnikNaForumuRepositroy extends JpaRepository<KorisnikNaForumu, Long> {

	@Query(value = "SELECT forum_korisnik.*"
			+ " FROM users"
			+ " INNER JOIN forum_korisnik ON users.id = forum_korisnik.user_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Optional<KorisnikNaForumu> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM forum_user_follow_tema WHERE tema_id = :id AND korisnik_na_forumu_id = :korisnikId", nativeQuery = true)
	Boolean getFollowTemaExists(@Param("id") Long id, @Param("korisnikId") Long korisnikId);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM forum_user_follow_podforum WHERE podforum_id = :id AND korisnik_na_forumu_id = :korisnikId", nativeQuery = true)
	Boolean getFollowPodforumExists(@Param("id") Long id, @Param("korisnikId") Long korisnikId);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END FROM forum_user_follow_tema WHERE tema_id = :id AND korisnik_na_forumu_id = :korisnikId", nativeQuery = true)
	Iterable<KorisnikNaForumu> getAllFollowTema(@Param("id") Long id, @Param("korisnikId") Long korisnikId);
	
	@Query(value = "SELECT forum_korisnik.*"
			+ " FROM users"
			+ " INNER JOIN forum_korisnik ON users.id = forum_korisnik.user_id"
			+ " INNER JOIN forum_user_follow_podforum ON forum_korisnik.id = forum_user_follow_podforum.korisnik_na_forumu_id"
			+ " WHERE forum_user_follow_podforum.podforum_id = :id", nativeQuery = true)
	Iterable<KorisnikNaForumu> getAllFollowPodforum(@Param("id") Long id);
	
	@Query(value = "SELECT forum_korisnik.*"
			+ " FROM users"
			+ " INNER JOIN forum_korisnik ON users.id = forum_korisnik.user_id"
			+ " INNER JOIN forum_user_follow_tema ON forum_korisnik.id = forum_user_follow_tema.korisnik_na_forumu_id"
			+ " WHERE forum_user_follow_tema.tema_id = :id", nativeQuery = true)
	Iterable<KorisnikNaForumu> getAllFollowTemu(@Param("id") Long id);
}
