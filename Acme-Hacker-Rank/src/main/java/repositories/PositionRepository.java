
package repositories;

import java.util.Collection;
import java.util.Date;

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
	Collection<Position> getAllPositionsFiltered();

	@Query("select p from Position p where p.draft = false and p.cancelled = false and p.company.id = ?1")
	Collection<Position> getAllPositionsFilteredOfCompany(Integer idCompany);

	@Query("select p from Position p where p.salaryOffered = 1*(select max(p.salaryOffered) from Position p)")
	Collection<Position> getPositionsWithTheBestSalary();

	@Query("select p from Position p where p.salaryOffered = 1*(select min(p.salaryOffered) from Position p)")
	Collection<Position> getPositionsWithTheWorstSalary();

	@Query("select p from Position p where p.draft = false and p.cancelled = false and not exists (select a.problem.position from Application a where a.hacker.id = ?1)")
	Collection<Position> getPositionsCanBeApplied(int idHacker);

	//FINDERS

	@Query("select distinct(p) from Position p JOIN p.skillsRequired s JOIN p.technologiesRequired t where (p.draft = false AND p.cancelled = false) AND (p.company.companyName LIKE %?1% OR p.title LIKE %?1% OR p.description LIKE %?1% OR p.profileRequired LIKE %?1% OR s LIKE %?1% OR t LIKE %?1%)")
	Collection<Position> getFilterPositionsByKeyword(String keyword);

	@Query("select distinct(p) from Position p JOIN p.skillsRequired s JOIN p.technologiesRequired t where (p.draft = false AND p.cancelled = false) AND (p.ticker.identifier LIKE %?1% OR p.title LIKE %?1% OR p.description LIKE %?1% OR p.profileRequired LIKE %?1% OR s LIKE %?1% OR t LIKE %?1%) AND (p.deadline BETWEEN ?2 and ?3) AND (p.salaryOffered > ?4)")
	Collection<Position> getFilterPositionsByFinder(String keyword, Date minimumdeadline, Date maximumDeadline, Double minimumSalary);

}
