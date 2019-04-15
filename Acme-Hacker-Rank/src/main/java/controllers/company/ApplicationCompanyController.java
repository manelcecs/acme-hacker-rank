
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AnswerService;
import services.ApplicationService;
import services.CompanyService;
import services.CurriculaService;
import services.PositionService;
import controllers.AbstractController;
import domain.Answer;
import domain.Application;
import domain.Company;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	CurriculaService	curriculaService;

	@Autowired
	CompanyService		companyService;

	@Autowired
	PositionService		positionService;

	@Autowired
	AnswerService		answerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("application/list");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());

		final Collection<Application> applications = this.applicationService.getApplicationsOfCompany(company.getId());

		result.addObject("applications", applications);
		result.addObject("requestURI", "application/hacker/list.do");
		this.configValues(result);

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int idApplication) {
		ModelAndView result = new ModelAndView("application/display");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());
		final Application application = this.applicationService.findOne(idApplication);

		if (application.getProblem().getPosition().getCompany().getId() != company.getId() && application.getStatus().equals("SUBMITTED"))
			result = new ModelAndView("redirect:list.do");
		else {

			final Answer answer = this.answerService.getAnswerOfApplication(idApplication);

			result.addObject("application", application);
			result.addObject("answer", answer);
			result.addObject("existsAnswer", true);

		}

		this.configValues(result);
		return result;

	}

	@RequestMapping(value = "/changeAccepted", method = RequestMethod.GET)
	public ModelAndView changeAccepted(@RequestParam(required = true) final int idApplication) {
		ModelAndView result;

		try {
			this.applicationService.changeStauts(idApplication, "ACCEPTED");
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?idApplication=" + idApplication);
		}

		return result;
	}

	@RequestMapping(value = "/changeRejected", method = RequestMethod.GET)
	public ModelAndView changeRejected(@RequestParam(required = true) final int idApplication) {
		ModelAndView result;

		try {
			this.applicationService.changeStauts(idApplication, "REJECTED");
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?idApplication=" + idApplication);
		}

		return result;
	}

	@RequestMapping(value = "/listByStatus", method = RequestMethod.GET)
	public ModelAndView listByStatus(@RequestParam(required = true) final String status) {
		final ModelAndView result = new ModelAndView("application/list");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());

		final Collection<Application> applications = this.applicationService.getApplicationsOfCompanyByStatus(company.getId(), status);

		result.addObject("applications", applications);
		result.addObject("requestURI", "application/hacker/listByStatus.do?status=" + status);
		this.configValues(result);
		return result;

	}

}
