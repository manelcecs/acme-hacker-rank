
package controllers.hacker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AnswerService;
import services.ApplicationService;
import services.CurriculaService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Answer;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import forms.ApplicationForm;

@Controller
@RequestMapping("/application/hacker")
public class ApplicationHackerController extends AbstractController {

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	CurriculaService	curriculaService;

	@Autowired
	HackerService		hackerService;

	@Autowired
	PositionService		positionService;

	@Autowired
	AnswerService		answerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("application/list");
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		final Collection<Application> applications = this.applicationService.getApplicationOfHacker(hacker.getId());
		final Collection<Application> applicationsAnswered = this.applicationService.getApplicationsAnswered(hacker.getId());

		result.addObject("applications", applications);
		result.addObject("applicationsAnswered", applicationsAnswered);
		result.addObject("requestURI", "application/hacker/list.do");
		this.configValues(result);
		return result;

	}

	@RequestMapping(value = "/listByStatus", method = RequestMethod.GET)
	public ModelAndView listByStatus(@RequestParam(required = true) final String status) {
		final ModelAndView result = new ModelAndView("application/list");
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		final Collection<Application> applications = this.applicationService.getApplicationOfHackerByStatus(hacker.getId(), status);
		final Collection<Application> applicationsAnswered = this.applicationService.getApplicationsAnswered(hacker.getId());

		result.addObject("applications", applications);
		result.addObject("applicationsAnswered", applicationsAnswered);
		result.addObject("requestURI", "application/hacker/list.do");
		this.configValues(result);
		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int idApplication) {
		ModelAndView result = new ModelAndView("application/display");
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		final Application application = this.applicationService.findOne(idApplication);

		if (application.getHacker().getId() != hacker.getId())
			result = new ModelAndView("redirect:list.do");
		else {

			boolean existsAnswer = false;
			final Answer answer = this.answerService.getAnswerOfApplication(idApplication);

			if (answer != null)
				existsAnswer = true;

			result.addObject("application", application);
			result.addObject("answer", answer);
			result.addObject("existsAnswer", existsAnswer);
			this.configValues(result);
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return this.createModelAndView(new ApplicationForm());

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final ApplicationForm applicationForm, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createModelAndView(applicationForm);
		else
			try {
				this.applicationService.newApplication(applicationForm);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(applicationForm, "cannot.commit.create");
			}
		return result;

	}

	protected ModelAndView createModelAndView(final ApplicationForm applicationForm) {
		return this.createModelAndView(applicationForm, null);

	}

	protected ModelAndView createModelAndView(final ApplicationForm applicationForm, final String message) {
		final ModelAndView result = new ModelAndView("application/create");

		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Position> positions = this.positionService.getPositionsCanBeApplied(hacker.getId());
		final Collection<Curricula> curriculas = this.curriculaService.findAllNoCopy(hacker);

		result.addObject("positions", positions);
		result.addObject("applicationForm", applicationForm);
		result.addObject("curriculas", curriculas);
		result.addObject("message", message);
		this.configValues(result);

		return result;
	}
}
