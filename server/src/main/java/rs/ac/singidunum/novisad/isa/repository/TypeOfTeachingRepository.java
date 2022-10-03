package rs.ac.singidunum.novisad.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.novisad.isa.model.TypeOfTeaching;

@Repository
public interface TypeOfTeachingRepository extends JpaRepository<TypeOfTeaching, Long> {

}
