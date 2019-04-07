
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> getPositionsOfCompany(int idCompany);

	@Query("select p from Position p where 1*(select count(pb) from Problem pb where pb.position.id = p.id)>1")
	Collection<Position> getPositionCanChangedraft();

	@Query("select p from Position p where p.draft = false and p.cancelled = false")
	Collection<Position> getAllParadesFiltered();

	@Query("select p from Position p where p.draft = false and p.cancelled = false and p.company.id = ?1")
	Collection<Position> getAllParadesFilteredOfCompany(Integer idCompany);

}
