
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.salaryOffered = 1*(select max(p.salaryOffered) from Position p)")
	Collection<Position> getPositionsWithTheBestSalary();

	@Query("select p from Position p where p.salaryOffered = 1*(select min(p.salaryOffered) from Position p)")
	Collection<Position> getPositionsWithTheWorstSalary();
}
