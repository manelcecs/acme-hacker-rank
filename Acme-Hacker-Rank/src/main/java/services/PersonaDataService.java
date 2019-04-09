
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Service
@Transactional
public class PersonaDataService {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private HackerService			hackerService;


	public PersonalData create() {
		final PersonalData res = new PersonalData();

		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		final StringBuilder fullName = new StringBuilder();
		fullName.append(hacker.getName() + " ");
		for (final String s : hacker.getSurnames())
			fullName.append(s + " ");
		res.setFullName(fullName.toString().trim());
		res.setPhoneNumber(hacker.getPhoneNumber());

		return res;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		return this.personalDataRepository.save(personalData);
	}

	public PersonalData findOne(final int personalDataId) {
		return this.personalDataRepository.findOne(personalDataId);
	}

	public PersonalData findByCurricula(final Curricula curricula) {
		return this.personalDataRepository.findByCurricula(curricula.getId());
	}

	public PersonalData createCopy(final PersonalData personalData, final Curricula curricula) {
		final PersonalData res = new PersonalData();

		res.setCurricula(curricula);
		res.setFullName(personalData.getFullName());
		res.setGitHubProfile(personalData.getGitHubProfile());
		res.setLinkedinProfile(personalData.getLinkedinProfile());
		res.setPhoneNumber(personalData.getPhoneNumber());
		res.setStatement(personalData.getStatement());

		return this.save(res);
	}

}
