package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.ScientificArea;

@Repository
public interface ScientificAreaRepository extends JpaRepository<ScientificArea, Long> {
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM scientific_area"
			+ " WHERE scientific_area.name = :name", nativeQuery = true)
	Boolean existScientificArea(@Param("name") String name);

}
