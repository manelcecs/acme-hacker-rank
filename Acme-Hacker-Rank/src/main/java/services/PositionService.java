
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
import repositories.TickerRepository;
import security.LoginService;
import security.UserAccount;
import utiles.AuthorityMethods;
import utiles.IntermediaryBetweenTransactions;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	@Autowired
	private TickerRepository				tickerRepository;

	@Autowired
	private CompanyService					companyService;

	@Autowired
	private PositionRepository				positionRepository;

	@Autowired
	private Validator						validator;

	@Autowired
	private ProblemService					problemService;

	@Autowired
	private MessageService					messageService;

	@Autowired
	private IntermediaryBetweenTransactions	intermediaryBetweenTransactions;


	public Position findOne(final int idPosition) {
		return this.positionRepository.findOne(idPosition);
	}

	public Position reconstruct(final PositionForm positionForm, final BindingResult binding) {
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());

		Position position;
		if (positionForm.getId() == 0) {
			position = new Position();
			position.setTicker(this.intermediaryBetweenTransactions.generateTicker(company.getCompanyName()));
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

		Assert.isTrue(!position.getCancelled());

		final UserAccount principal = LoginService.getPrincipal();

		final Company company = this.companyService.findByPrincipal(principal);
		if (position.getId() != 0) {
			final Position positionOld = this.findOne(position.getId());
			Assert.isTrue(positionOld.getCompany().getId() == company.getId());
			Assert.isTrue(!positionOld.getCancelled());
			Assert.isTrue(positionOld.getDraft());
		}

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

	public Collection<Position> getFilterPositionsByFinder(final String keyword, final Date minimumdeadline, final Date maximumDeadline, final Double minimumSalary) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER") || AuthorityMethods.chechAuthorityLogged("COMPANY"));
		return this.positionRepository.getFilterPositionsByFinder(keyword, minimumdeadline, maximumDeadline, minimumSalary);
	}

	public Collection<Position> getPositionsOfCompany(final int idCompany) {
		return this.positionRepository.getPositionsOfCompany(idCompany);
	}

	public Position changeDraft(final Position position) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final UserAccount principal = LoginService.getPrincipal();

		final Company company = this.companyService.findByPrincipal(principal);

		Assert.isTrue(position.getCompany().getId() == company.getId());

		final Collection<Problem> problems = this.problemService.getProblemsOfPositionFinal(position.getId());
		Assert.isTrue(problems.size() >= 2);

		position.setDraft(false);

		final Position newPosition = this.positionRepository.save(position);

		this.messageService.notificationNewPositionMatchFinder(newPosition);
		return newPosition;

	}

	public void delete(final Position position) {

		final UserAccount principal = LoginService.getPrincipal();

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final Company company = this.companyService.findByPrincipal(principal);

		Assert.isTrue(position.getCompany().getId() == company.getId());

		Assert.isTrue(position.getDraft());
		Assert.isTrue(!position.getCancelled());

		final Collection<Problem> problems = this.problemService.getProblemsOfPosition(position.getId());

		this.problemService.deleteCollectionOfProblems(problems);
		this.positionRepository.delete(position);
		this.tickerRepository.delete(position.getTicker());

	}

	public Position changeCancellation(final Position position) {

		final UserAccount principal = LoginService.getPrincipal();

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final Company company = this.companyService.findByPrincipal(principal);

		Assert.isTrue(position.getCompany().getId() == company.getId());

		Assert.isTrue(!position.getDraft());

		position.setCancelled(!position.getCancelled());

		return this.positionRepository.save(position);

	}

	public Collection<Position> getPositions(final int idFinder) {
		return this.positionRepository.getPositionsByFinder(idFinder);
	}

	public Collection<Position> getPositionCanChangedraft() {
		return this.positionRepository.getPositionCanChangedraft();
	}

	public Collection<Position> getAllPositionsFiltered() {
		return this.positionRepository.getAllPositionsFiltered();
	}

	public Collection<Position> getAllPositionsFilteredOfCompany(final Integer idCompany) {
		return this.positionRepository.getAllPositionsFilteredOfCompany(idCompany);
	}

	public Collection<Position> getPositionsCanBeApplied(final int idHacker) {
		return this.positionRepository.getPositionsCanBeApplied(idHacker);
	}

	public void flush() {
		this.positionRepository.flush();
	}
}
