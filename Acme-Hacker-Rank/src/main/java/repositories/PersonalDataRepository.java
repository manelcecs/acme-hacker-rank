
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Integer> {

	@Query("select p from PersonalData p where p.curricula.id = ?1")
	PersonalData findByCurricula(final int curriculaId);

}
