
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.SocialProfile;

public interface SocialProfileRepository extends JpaRepository<SocialProfile, Integer> {

	@Query("select distinct(sp) from SocialProfile sp where sp.actor.id = ?1")
	public Collection<SocialProfile> findAllSocialProfile(final int actorId);

}
