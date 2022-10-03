package rs.ac.singidunum.novisad.isa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.Rank;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
	
	@Query(value = "SELECT ranks.*"
			+ " FROM users"
			+ " INNER JOIN professors ON users.id = professors.user_id"
			+ " INNER JOIN ranks ON professors.id = ranks.professor_id"
			+ " WHERE users.username = :username", nativeQuery = true)
	Iterable<Rank> findByUsername(@Param("username") String username);
}
