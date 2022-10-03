package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

	@Query(value = "SELECT CASE WHEN COUNT(*) >= 1 THEN 'true' ELSE 'false' END "
			+ " FROM files"
			+ " WHERE files.url = :url", nativeQuery = true)
	Boolean existFileByURL(@Param("url") String url);
}
