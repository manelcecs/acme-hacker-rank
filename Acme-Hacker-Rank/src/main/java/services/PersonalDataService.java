
package services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import security.LoginService;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;
import forms.CurriculaAndPersonalDataForm;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private Validator				validator;

	@Autowired
	private AdminConfigService		adminConfigService;


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

	public PersonalData reconstruct(final CurriculaAndPersonalDataForm curriculaPersonalForm, final int curriculaId, final BindingResult binding) {
		final PersonalData res = this.create(curriculaId);
		final Curricula curricula = this.curriculaService.findOne(curriculaId);

		res.setCurricula(curricula);
		res.setFullName(curriculaPersonalForm.getFullName());
		res.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), curriculaPersonalForm.getPhoneNumber()));
		res.setStatement(curriculaPersonalForm.getStatement());
		res.setGitHubProfile(curriculaPersonalForm.getGitHubProfile());
		res.setLinkedinProfile(curriculaPersonalForm.getLinkedinProfile());

		if (!res.getGitHubProfile().toLowerCase().contains("https://github.com/"))
			binding.rejectValue("gitHubProfile", "curricula.edit.githubProfile.error");

		if ((!res.getLinkedinProfile().toLowerCase().contains("https://linkedin.com/")))
			binding.rejectValue("linkedinProfile", "curricula.edit.linkedInProfile.error");

		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public PersonalData reconstruct(final PersonalData personalData, final BindingResult binding) {

		final PersonalData res = this.findOne(personalData.getId());

		res.setFullName(personalData.getFullName());
		res.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), personalData.getPhoneNumber()));
		res.setStatement(personalData.getStatement());
		res.setGitHubProfile(personalData.getGitHubProfile());
		res.setLinkedinProfile(personalData.getLinkedinProfile());

		if (!res.getGitHubProfile().toLowerCase().contains("https://github.com/"))
			binding.rejectValue("gitHubProfile", "curricula.edit.githubProfile.error");

		if ((!res.getLinkedinProfile().toLowerCase().contains("https://linkedin.com/")))
			binding.rejectValue("linkedinProfile", "curricula.edit.linkedInProfile.error");

		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;

	}

	public void delete(final PersonalData personalData) {
		this.personalDataRepository.delete(personalData);
	}
}
