
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Company;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	public Collection<Problem> getProblemsOfParade(final int idPosition) {
		return this.problemRepository.getProblemsOfParade(idPosition);
	}

	public void deleteCollectionOfProblems(final Collection<Problem> problems) {
		this.problemRepository.delete(problems);
	}

	public Problem findOne(final int idProblem) {
		return this.problemRepository.findOne(idProblem);
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		Problem result;
		if (problem.getPosition() == null)
			binding.rejectValue("position", "problem.edit.position.error");
		if (problem.getId() == 0)
			result = problem;
		else {
			result = this.problemRepository.findOne(problem.getId());
			result.setTitle(problem.getTitle());
			result.setPosition(problem.getPosition());
			result.setStatement(problem.getStatement());
			result.setHint(problem.getHint());
			result.setAttachments(problem.getAttachments());
			result.setDraft(problem.getDraft());
		}
		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Problem save(final Problem problem) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final UserAccount principal = LoginService.getPrincipal();
		final Company company = this.companyService.findByPrincipal(principal.getId());
		if (problem.getId() != 0)
			Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		return this.problemRepository.save(problem);
	}

	public Problem changeDraft(final Problem problem) {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		problem.setDraft(false);

		return this.problemRepository.save(problem);

	}

	public void delete(final Problem problem) {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		Assert.isTrue(problem.getDraft());

		this.problemRepository.delete(problem);

	}

	public Collection<Problem> getProblemsOfCompany(final int idCompany) {
		return this.problemRepository.getProblemsOfCompany(idCompany);
	}

}
