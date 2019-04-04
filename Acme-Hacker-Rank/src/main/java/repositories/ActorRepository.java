
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	public Actor getByUserAccount(int userAccountId);

	@Query("select a from Actor a join a.messageBoxes mb where mb.id = ?1")
	public Actor getByMessageBox(int idBox);

}
