
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select p from Problem p where p.position.id = ?1")
	Collection<Problem> getProblemsOfPosition(int idPosition);

	@Query("select p from Problem p join p.position pt where pt.company.id = ?1")
	Collection<Problem> getProblemsOfCompany(int idCompany);

	@Query("select p from Problem p where p.position.id = ?1 and p.draft=false")
	Collection<Problem> getProblemsOfPositionFinal(int idPosition);

}
