package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.TeachingMaterial;

@Repository
public interface TeachingMaterialRepository extends JpaRepository<TeachingMaterial, Long>   {
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			+ " FROM teaching_materials"
			+ " WHERE teaching_materials.naziv = :naziv", nativeQuery = true)
	Boolean existsByNaziv(@Param("naziv") String naziv);
	
	@Query(value = "SELECT teaching_materials_authors.authors FROM teaching_materials_authors", nativeQuery = true)
	Iterable<String> getAllAuthors();
}
