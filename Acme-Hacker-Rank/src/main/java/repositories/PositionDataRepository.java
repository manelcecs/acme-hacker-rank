
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.PositionData;

public interface PositionDataRepository extends JpaRepository<PositionData, Integer> {

	@Query("select distinct(p) from PositionData p where p.curricula.id = ?1")
	public Collection<PositionData> findAllCurricula(final int curriculaId);

}
