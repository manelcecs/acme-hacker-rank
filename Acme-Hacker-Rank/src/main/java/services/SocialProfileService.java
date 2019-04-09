
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;


	public SocialProfile create() {
		final SocialProfile res = new SocialProfile();
		res.setActor(this.actorService.findByUserAccount(LoginService.getPrincipal()));
		return res;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		SocialProfile res;
		if (socialProfile.getId() == 0)
			Assert.isTrue(socialProfile.getActor().getId() == this.actorService.findByUserAccount(LoginService.getPrincipal()).getId());
		res = this.socialProfileRepository.saveAndFlush(socialProfile);
		return res;

	}

	public SocialProfile findOne(final int socialProfileId) {
		return this.socialProfileRepository.findOne(socialProfileId);
	}

	public Collection<SocialProfile> findAllSocialProfiles() {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		return this.socialProfileRepository.findAllSocialProfile(this.actorService.findByUserAccount(LoginService.getPrincipal()).getId());
	}

}
