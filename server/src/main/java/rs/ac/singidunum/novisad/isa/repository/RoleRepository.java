package rs.ac.singidunum.novisad.isa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.ERole;
import rs.ac.singidunum.novisad.isa.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(ERole name);
	
	@Query(value = "SELECT COUNT(*) FROM roles INNER JOIN user_roles ON roles.id = user_roles.role_id WHERE roles.name = 'ROLE_ADMINISTRATOR'", nativeQuery = true)
	int countAdministrator();
	
}
