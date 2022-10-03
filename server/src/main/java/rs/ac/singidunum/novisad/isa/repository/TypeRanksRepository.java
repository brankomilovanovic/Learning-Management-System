package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.TypeRanks;

@Repository
public interface TypeRanksRepository extends JpaRepository<TypeRanks, Long>  {

	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM type_ranks"
			+ " WHERE type_ranks.name = :name", nativeQuery = true)
	Boolean existByName(@Param("name") String name);
	
}
