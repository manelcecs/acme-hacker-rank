
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculaRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import forms.CurriculaAndPersonalDataForm;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository			curriculaRepository;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private Validator					validator;


	public Curricula create() {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));
		final Curricula res = new Curricula();
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		res.setCopy(false);
		res.setHacker(hacker);

		return res;
	}

	public void delete(final Curricula curricula) {
		if (curricula.getId() != 0) {
			final Collection<MiscellaneousData> miscellaneousData = this.miscellaneousDataService.findAllCurricula(curricula);
			final Collection<PositionData> positionData = this.positionDataService.findAllCurricula(curricula);
			final Collection<EducationData> educationsData = this.educationDataService.findAllCurricula(curricula);
			final PersonalData personalData = this.personalDataService.findByCurricula(curricula);

			for (final MiscellaneousData misc : miscellaneousData)
				this.miscellaneousDataService.delete(misc);

			for (final PositionData pos : positionData)
				this.positionDataService.delete(pos);

			for (final EducationData edu : educationsData)
				this.educationDataService.delete(edu);

			this.personalDataService.delete(personalData);

			this.curriculaRepository.delete(curricula);
		}
	}

	public Curricula createCopy(final Curricula curricula) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(curricula.getHacker().getId() == hacker.getId());

		final Curricula newCur = new Curricula();

		newCur.setTitle(curricula.getTitle());
		newCur.setHacker(hacker);
		newCur.setCopy(true);

		final Curricula res = this.save(newCur);

		this.personalDataService.createCopy(this.personalDataService.findByCurricula(curricula), res);

		for (final EducationData edu : this.educationDataService.findAllCurricula(curricula))
			this.educationDataService.createCopy(res, edu);

		for (final MiscellaneousData misc : this.miscellaneousDataService.findAllCurricula(curricula))
			this.miscellaneousDataService.createCopy(res, misc);

		for (final PositionData pos : this.positionDataService.findAllCurricula(curricula))
			this.positionDataService.createCopy(res, pos);

		return res;
	}
	public Curricula save(final Curricula curricula) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(curricula.getHacker().getId() == hacker.getId());

		final Curricula res = this.curriculaRepository.save(curricula);

		return res;

	}

	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Collection<Curricula> findAllNoCopy(final Hacker hacker) {
		return this.curriculaRepository.findAllNoCopy(hacker.getId());
	}

	public Collection<Curricula> findAllApplication(final int applicationId) {
		return this.curriculaRepository.findAllApplication(applicationId);
	}

	public Collection<Curricula> getCurriculasOfHacker(final int idHacker) {
		return this.curriculaRepository.getCurriculasOfHacker(idHacker);
	}

	public Curricula reconstruct(final CurriculaAndPersonalDataForm curriculaPersonalForm, final BindingResult binding) {
		final Curricula result;

		result = this.create();

		result.setTitle(curriculaPersonalForm.getTitle());

		this.validator.validate(result, binding);

		//Validar la personalData
		if (curriculaPersonalForm.getFullName().equals(""))
			binding.rejectValue("fullName", "org.hibernate.validator.constraints.NotBlank.message");
		if (curriculaPersonalForm.getStatement().equals(""))
			binding.rejectValue("statement", "org.hibernate.validator.constraints.NotBlank.message");
		if (curriculaPersonalForm.getPhoneNumber().equals(""))
			binding.rejectValue("phoneNumber", "org.hibernate.validator.constraints.NotBlank.message");
		if (curriculaPersonalForm.getGitHubProfile().equals(""))
			binding.rejectValue("gitHubProfile", "org.hibernate.validator.constraints.NotBlank.message");
		if (curriculaPersonalForm.getLinkedinProfile().equals(""))
			binding.rejectValue("linkedinProfile", "org.hibernate.validator.constraints.NotBlank.message");

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public void delete(final Collection<Curricula> curricula) {
		this.curriculaRepository.delete(curricula);
	}

	public Collection<Curricula> findAllCopy(final Hacker hacker) {
		return this.curriculaRepository.findAllCopy(hacker.getId());
	}

	public void flush() {
		this.curriculaRepository.flush();

	}
}
