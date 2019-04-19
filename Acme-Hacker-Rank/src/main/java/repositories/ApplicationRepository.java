
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.problem.position.company.id = ?1 and a.status != 'PENDING'")
	Collection<Application> getApplicationsOfCompany(int idCompany);

	@Query("select a from Application a where a.problem.position.company.id = ?1 and a.status = ?2")
	Collection<Application> getApplicationsOfCompanyByStatus(int idCompany, String stauts);

	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> getApplicationOfHacker(int idHacker);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = ?2")
	Collection<Application> getApplicationOfHackerByStatus(int idHacker, String stauts);

	@Query("select a.application from Answer a where a.application.hacker.id=?1")
	Collection<Application> getApplicationsAnswered(int idHacker);

}
