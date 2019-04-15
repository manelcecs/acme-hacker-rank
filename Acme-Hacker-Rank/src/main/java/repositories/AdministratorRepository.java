
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int principalId);

	//DASHBOARD--------------------------------------------------------------
	@Query("select avg(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Double getAvgOfPositionsPerCompany();

	@Query("select min(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Integer getMinimumOfPositionsPerCompany();

	@Query("select max(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Integer getMaximumOfPositionsPerCompany();

	@Query("select stddev(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Double getSDOfPositionsPerCompany();

	//---------------------------------------------------------------------------------

	@Query("select avg(1*(select count(a) from Application a where a.hacker.id = h.id)) from Hacker h")
	Double getAvgOfApplicationsPerHacker();

	@Query("select min(1*(select count(a) from Application a where a.hacker.id = h.id)) from Hacker h")
	Integer getMinimumOfApplicationsPerHacker();

	@Query("select max(1*(select count(a) from Application a where a.hacker.id = h.id)) from Hacker h")
	Integer getMaximumOfApplicationsPerHacker();

	@Query("select stddev(1*(select count(a) from Application a where a.hacker.id = h.id)) from Hacker h")
	Double getSDOfApplicationsPerHacker();

	//----------------------------------------------------------------------------

	@Query("select avg(p.salaryOffered) from Position p where p.draft = false")
	Double getAvgOfSalariesOffered();

	@Query("select min(p.salaryOffered) from Position p where p.draft = false")
	Integer getMinimumOfSalariesOffered();

	@Query("select max(p.salaryOffered) from Position p where p.draft = false")
	Integer getMaximumOfSalariesOffered();

	@Query("select stddev(p.salaryOffered) from Position p where p.draft = false")
	Double getSDOfSalariesOffered();

	//-------------------------------------------------------------------

	@Query("select avg(1*(select count(c) from Curricula c where c.hacker.id = h.id and c.copy = false)) from Hacker h")
	Double getAvgOfCurriculaPerHacker();

	@Query("select min(1*(select count(c) from Curricula c where c.hacker.id = h.id and c.copy = false)) from Hacker h")
	Integer getMinimumOfCurriculaPerHacker();

	@Query("select max(1*(select count(c) from Curricula c where c.hacker.id = h.id and c.copy = false)) from Hacker h")
	Integer getMaximumOfCurriculaPerHacker();

	@Query("select stddev(1*(select count(c) from Curricula c where c.hacker.id = h.id and c.copy = false)) from Hacker h")
	Double getSDOfCurriculaPerHacker();

	//-------------------------------------------------------------------

	@Query("select avg(f.positions.size) from Finder f")
	Double getAvgOfResultsInFinders();

	@Query("select min(f.positions.size) from Finder f")
	Integer getMinimumOfResultsInFinders();

	@Query("select max(f.positions.size) from Finder f")
	Integer getMaximumOfResultsInFinders();

	@Query("select stddev(f.positions.size) from Finder f")
	Double getSDOfResultsInFinders();

	@Query("select 1.0 * count(f) / (select count(fn) from Finder fn where fn.positions.size != 0) from Finder f where f.positions.size = 0")
	Double getRatioOfEmptyVsNotEmptyFinders();

}
