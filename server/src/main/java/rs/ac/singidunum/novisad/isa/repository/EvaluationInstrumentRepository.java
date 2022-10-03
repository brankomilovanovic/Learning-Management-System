package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.EvaluationInstrument;

@Repository
public interface EvaluationInstrumentRepository extends JpaRepository<EvaluationInstrument, Long>  {

	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			 + " FROM evaluation_instruments"
			 + " WHERE evaluation_instruments.file_id = :id", nativeQuery = true)
	Boolean existsByFileID(@Param("id") Long id);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END"
			 + " FROM evaluation_instruments"
			 + " WHERE evaluation_instruments.tip_testiranja = :tipTestiranja", nativeQuery = true)
	Boolean existsByTipTestiranja(@Param("tipTestiranja") String tipTestiranja);
}
