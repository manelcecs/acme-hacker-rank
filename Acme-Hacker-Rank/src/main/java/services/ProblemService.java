
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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


	public Collection<Problem> getProblemsOfPosition(final int idPosition) {
		return this.problemRepository.getProblemsOfPosition(idPosition);

	}

	public void deleteCollectionOfProblems(final Collection<Problem> problems) {
		this.problemRepository.delete(problems);
	}

	public Problem findOne(final int idProblem) {
		return this.problemRepository.findOne(idProblem);
	}

	public Problem save(final Problem problem) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));
		final UserAccount principal = LoginService.getPrincipal();
		final Company company = this.companyService.findByPrincipal(principal);
		if (problem.getId() != 0) {
			final Problem problemBD = this.problemRepository.findOne(problem.getId());
			Assert.isTrue(problemBD.getDraft());
		}
		Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		return this.problemRepository.save(problem);
	}

	public Problem changeDraft(final Problem problem) {
		final Problem problemBD = this.problemRepository.findOne(problem.getId());
		Assert.isTrue(problemBD.getDraft());
		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal);

		Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		problem.setDraft(false);

		return this.problemRepository.save(problem);

	}

	public void delete(final Problem problem) {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("COMPANY"));

		final Company company = this.companyService.findByPrincipal(principal);

		Assert.isTrue(problem.getPosition().getCompany().getId() == company.getId());

		Assert.isTrue(problem.getDraft());

		this.problemRepository.delete(problem);

	}

	public Collection<Problem> getProblemsOfCompany(final int idCompany) {
		return this.problemRepository.getProblemsOfCompany(idCompany);
	}

}
