
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select distinct(c) from Curricula c where c.hacker.id = ?1 AND c.copy = false")
	public Collection<Curricula> findAllNoCopy(final int hackerId);

	@Query("select distinct(c) from Application a join a.curricula c where a.id = ?1")
	public Collection<Curricula> findAllApplication(final int applicationId);

}
