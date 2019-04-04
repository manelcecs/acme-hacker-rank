
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	/**
	 * Queries the database to find all the messages sent by an actor
	 * 
	 * @param actorId
	 *            The id of the actor
	 * @return All of their messages
	 */
	@Query("select m from Message m where m.sender.id = ?1")
	public Collection<Message> findAllByActor(int actorId);

	@Query("select m.recipients from Message m where m.id = ?1")
	public Collection<Actor> getRecipients(int idMessage);

}
