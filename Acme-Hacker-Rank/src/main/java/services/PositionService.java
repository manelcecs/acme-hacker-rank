
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	@Autowired
	TickerService		tickerService;

	@Autowired
	CompanyService		companyService;

	@Autowired
	PositionRepository	positionRepository;

	@Autowired
	Validator			validator;

	@Autowired
	ProblemService		problemService;


	public Position findOne(final int idPosition) {
		return this.positionRepository.findOne(idPosition);
	}

	public Position reconstruct(final PositionForm positionForm, final BindingResult binding) {
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());

		Position position;
		if (positionForm.getId() == 0) {
			position = new Position();
			position.setTicker(this.tickerService.generateTicker(company.getCompanyName()));
			position.setCancelled(false);
			position.setDraft(true);
			position.setCompany(company);
		} else
			position = this.positionRepository.findOne(positionForm.getId());

		position.setTitle(positionForm.getTitle());
		position.setDescription(positionForm.getDescription());
		position.setDeadline(positionForm.getDeadline());
		position.setProfileRequired(positionForm.getProfileRequired());
		position.setSkillsRequired(positionForm.getSkillsRequired());
		position.setTechnologiesRequired(positionForm.getTechnologiesRequired());
		position.setSalaryOffered(positionForm.getSalaryOffered());

		this.validator.validate(position, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return position;
	}

	public PositionForm castToForm(final Position position) {
		final PositionForm positionForm = new PositionForm();

		positionForm.setId(position.getId());
		positionForm.setVersion(position.getVersion());

		positionForm.setTitle(position.getTitle());
		positionForm.setDescription(position.getDescription());
		positionForm.setDeadline(position.getDeadline());
		positionForm.setProfileRequired(position.getProfileRequired());
		positionForm.setSkillsRequired(position.getSkillsRequired());
		positionForm.setTechnologiesRequired(position.getTechnologiesRequired());
		positionForm.setSalaryOffered(position.getSalaryOffered());

		return positionForm;
	}

	public Position save(final Position position) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		Assert.isTrue(position.getDraft());

		final UserAccount principal = LoginService.getPrincipal();

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		return this.positionRepository.save(position);
	}

	//DASHBOARD-------------------------------------------------------------------------

	public Collection<Position> getPositionsWithTheBestSalary() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.positionRepository.getPositionsWithTheBestSalary();
	}

	public Collection<Position> getPositionsWithTheWorstSalary() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.positionRepository.getPositionsWithTheWorstSalary();
	}
	//FINDER-------------------------------------------------------------------

	public Collection<Position> getFilterPositionsByKeyword(final String keyword) {
		return this.positionRepository.getFilterPositionsByKeyword(keyword);
	}

	public Collection<Position> getFilterPositionsByFinder(final String keyword, final Date deadlineA, final Date deadlineB, final Date maximumDeadline, final Double minimumSalary) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));
		return this.positionRepository.getFilterPositionsByFinder(keyword, deadlineA, deadlineB, maximumDeadline, minimumSalary);
	}

	public Collection<Position> getPositionsOfCompany(final int idCompany) {
		return this.positionRepository.getPositionsOfCompany(idCompany);
	}

	public Position changeDraft(final Position position) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final UserAccount principal = LoginService.getPrincipal();

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		//FIXME: Cuando se puedan meter problemas, descomentar esto
		//		final Collection<Problem> problems = this.problemService.getProblemsOfParade(position.getId());
		//		Assert.isTrue(problems.size() >= 2);

		position.setDraft(false);

		return this.positionRepository.save(position);

	}

	//TODO: Al hacer delete hay que borrar la position en el finder? En principio si pero hay que echarle un ojo al rendimiento
	public void delete(final Position position) {

		final UserAccount principal = LoginService.getPrincipal();

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		Assert.isTrue(position.getDraft());

		final Collection<Problem> problems = this.problemService.getProblemsOfPosition(position.getId());

		this.problemService.deleteCollectionOfProblems(problems);
		this.positionRepository.delete(position);
		this.tickerService.delete(position.getTicker());

	}

	public Position changeCancellation(final Position position) {

		final UserAccount principal = LoginService.getPrincipal();

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		Assert.isTrue(!position.getDraft());

		position.setCancelled(!position.getCancelled());

		return this.positionRepository.save(position);

	}

	public Collection<Position> getPositionCanChangedraft() {
		return this.positionRepository.getPositionCanChangedraft();
	}

	public Collection<Position> getAllParadesFiltered() {
		return this.positionRepository.getAllParadesFiltered();
	}

	public Collection<Position> getAllParadesFilteredOfCompany(final Integer idCompany) {
		return this.positionRepository.getAllParadesFilteredOfCompany(idCompany);
	}
}
