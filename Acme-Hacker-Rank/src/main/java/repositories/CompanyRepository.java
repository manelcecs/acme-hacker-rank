
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select a from Company a where a.userAccount.id = ?1")
	Company findByPrincipal(int principalId);

	@Query("select c from Company c where (1*(Select count(p) from Position p where p.company.id =c.id and p.draft = false) = (select max(1*(select count(p) from Position p where p.company.id = c.id and p.draft = false)) from Company c))")
	Collection<Company> getCompaniesWithMoreOffersOfPositions();
}
