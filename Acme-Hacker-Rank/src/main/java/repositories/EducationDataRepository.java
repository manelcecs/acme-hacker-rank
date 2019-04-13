
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.EducationData;

public interface EducationDataRepository extends JpaRepository<EducationData, Integer> {

	@Query("select distinct(ed) from EducationData ed where ed.curricula.id = ?1")
	public Collection<EducationData> findAllCurricula(final int curriculaId);

}
