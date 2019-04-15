
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
public class PersonalDataService {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;


	public PersonalData create(final int curriculaId) {
		final PersonalData res = new PersonalData();

		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		final Curricula c = this.curriculaService.findOne(curriculaId);
		res.setCurricula(c);
		final StringBuilder fullName = new StringBuilder();
		fullName.append(hacker.getName() + " ");
		for (final String s : hacker.getSurnames())
			fullName.append(s + " ");
		res.setFullName(fullName.toString().trim());
		res.setPhoneNumber(hacker.getPhoneNumber());

		return res;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.isTrue(personalData != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		if (personalData.getId() != 0)
			Assert.isTrue(!personalData.getCurricula().getCopy());

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
