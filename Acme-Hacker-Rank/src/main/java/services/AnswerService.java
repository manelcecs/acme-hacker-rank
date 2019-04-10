
package services;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Answer;
import domain.Application;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class AnswerService {

	@Autowired
	AnswerRepository	answerRepository;

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	HackerService		hackerService;


	public Answer create(final int idApplication) {
		AuthorityMethods.chechAuthorityLogged("HACKER");

		final Application application = this.applicationService.findOne(idApplication);
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.notNull(application);
		Assert.isTrue(application.getHacker().getId() == hacker.getId());

		final Position position = application.getProblem().getPosition();

		Assert.isTrue(!position.getCancelled());
		Assert.isTrue(position.getDeadline().after(DateTime.now().toDate()));

		Assert.isNull(this.getAnswerOfApplication(idApplication));

		final Answer answer = new Answer();
		answer.setApplication(application);

		return answer;
	}

	public Answer save(final Answer answer) {
		AuthorityMethods.chechAuthorityLogged("HACKER");

		final Application application = answer.getApplication();
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.notNull(application);
		Assert.isTrue(application.getHacker().getId() == hacker.getId());

		final Position position = application.getProblem().getPosition();

		Assert.isTrue(!position.getCancelled());
		Assert.isTrue(position.getDeadline().after(DateTime.now().toDate()));

		Assert.isNull(this.getAnswerOfApplication(application.getId()));

		final Answer result = this.answerRepository.save(answer);
		application.setStatus("SUBMITTED");
		application.setMomentSubmit(DateTime.now().toDate());
		this.applicationService.save(application);

		return result;

	}

	public Answer getAnswerOfApplication(final int idApplication) {
		return this.answerRepository.getAnswerOfApplication(idApplication);
	}

}
