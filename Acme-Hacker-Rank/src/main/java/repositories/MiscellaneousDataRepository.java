
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.MiscellaneousData;

public interface MiscellaneousDataRepository extends JpaRepository<MiscellaneousData, Integer> {

	@Query("select distinct(m) from MiscellaneousData m where m.curricula.id = ?1")
	public Collection<MiscellaneousData> findAllCurricula(final int curriculaId);

}
