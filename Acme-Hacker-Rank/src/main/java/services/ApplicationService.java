
package services;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import domain.Problem;
import forms.ApplicationForm;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CurriculaService		curriculaService;


	public Application newApplication(final ApplicationForm applicationForm) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		final Position position = applicationForm.getPosition();

		final Curricula curricula = applicationForm.getCurricula();

		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(!position.getDraft() && !position.getCancelled());

		Assert.isTrue(curricula.getHacker().getId() == hacker.getId());

		final Application application = new Application();
		application.setHacker(hacker);

		final Curricula copy = this.curriculaService.createCopy(curricula);
		application.setCurricula(copy);

		application.setMoment(DateTime.now().toDate());
		application.setStatus("PENDING");

		final List<Problem> problems = (List<Problem>) this.problemService.getProblemsOfPosition(position.getId());
		final Problem problem = problems.get(new Random().nextInt(problems.size()));
		application.setProblem(problem);

		return this.applicationRepository.save(application);
	}
	public Application findOne(final int idApplication) {
		return this.applicationRepository.findOne(idApplication);
	}

	public Application save(final Application application) {
		return this.applicationRepository.save(application);
	}

	public Application changeStauts(final int idApplication, final String status) {
		final Application application = this.findOne(idApplication);
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());

		Assert.notNull(application);
		Assert.isTrue(application.getProblem().getPosition().getCompany().equals(company));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		Assert.isTrue(status.equals("ACCEPTED") || status.equals("REJECTED"));

		application.setStatus(status);

		final Application applicationSave = this.save(application);

		this.messageService.notificationChangeStatus(applicationSave, company);

		return applicationSave;

	}

	public Collection<Application> getApplicationsOfCompany(final int idCompany) {
		return this.applicationRepository.getApplicationsOfCompany(idCompany);
	}

	public Collection<Application> getApplicationsOfCompanyByStatus(final int idCompany, final String status) {
		Assert.isTrue(status.equals("SUBMITTED") || status.equals("REJECTED") || status.equals("ACCEPTED"));
		return this.applicationRepository.getApplicationsOfCompanyByStatus(idCompany, status);
	}

	public Collection<Application> getApplicationOfHacker(final int idHacker) {
		return this.applicationRepository.getApplicationOfHacker(idHacker);
	}

	public Collection<Application> getApplicationOfHackerByStatus(final int idHacker, final String status) {
		Assert.isTrue(status.equals("SUBMITTED") || status.equals("REJECTED") || status.equals("ACCEPTED") || status.equals("PENDING"));
		return this.applicationRepository.getApplicationOfHackerByStatus(idHacker, status);
	}

	public Collection<Application> getApplicationsAnswered(final int idHacker) {
		return this.applicationRepository.getApplicationsAnswered(idHacker);
	}
}
