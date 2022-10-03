package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.SubjectNotifications;

@Repository
public interface SubjectNotificationsRepository extends JpaRepository<SubjectNotifications, Long> {

	@Query(value = "SELECT subject_notifications.* FROM subject_notifications WHERE subject_notifications.subject_id = :id ORDER BY subject_notifications.id DESC", nativeQuery = true)
	Iterable<SubjectNotifications> findBySubjectID(@Param("id") Long id);
	
	
}
