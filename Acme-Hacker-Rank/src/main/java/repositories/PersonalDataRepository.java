
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.PersonalData;

public interface PersonalDataRepository extends JpaRepository<PersonalData, Integer> {

	@Query("select p from PersonalData p where p.curricula = ?1")
	public PersonalData findByCurricula(final int curriculaId);

}
