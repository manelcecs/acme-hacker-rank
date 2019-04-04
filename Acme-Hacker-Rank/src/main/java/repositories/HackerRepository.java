
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select a from Hacker a where a.userAccount.id = ?1")
	Hacker findByPrincipal(int principalId);

	@Query("select h from Hacker h where (1*(Select count(a) from Application a where a.hacker.id =h.id) = (select max(1*(select count(a) from Application a where a.hacker.id = h.id)) from Hacker h))")
	Collection<Hacker> getHackersWithMoreApplications();
}
