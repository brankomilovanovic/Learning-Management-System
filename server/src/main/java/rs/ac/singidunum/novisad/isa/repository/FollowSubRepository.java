package rs.ac.singidunum.novisad.isa.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.singidunum.novisad.isa.model.FollowSub;

public interface FollowSubRepository extends JpaRepository<FollowSub, Long>{
	
	@Query(value = "SELECT * FROM follow_sub WHERE user_id = :id", nativeQuery = true)
	Iterable<FollowSub> findByUser_id(@Param("id") Long id);
	
	@Query(value = "SELECT user_id"
			+ " FROM follow_sub"
			+ " INNER JOIN follow_sub_subjects ON follow_sub.id = follow_sub_subjects.follow_sub_id"
			+ " WHERE follow_sub_subjects.subjects_id = :subjectId", nativeQuery = true)
	Iterable<BigInteger> findStudentOnSubject(@Param("subjectId") Long subjectId);

	
	
}