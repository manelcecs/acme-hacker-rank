
package controllers.company;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import utiles.ValidateCollectionURL;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final Problem problem = new Problem();
		return this.createEditModelAndView(problem);

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idProblem) {
		final ModelAndView result;

		final Problem problem = this.problemService.findOne(idProblem);
		result = this.createEditModelAndView(problem);

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Problem problem, final BindingResult binding) {
		ModelAndView result;
		if (problem.getAttachments() != null) {
			final Collection<String> urls = ValidateCollectionURL.deleteURLBlanksInCollection(problem.getAttachments());
			problem.setAttachments(urls);
		}
		try {
			final Problem problemRec = this.problemService.reconstruct(problem, binding);
			this.problemService.save(problemRec);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(problem);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(problem, "cannot.save.problem");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView(null);
	}

	@RequestMapping(value = "/changeDraft", method = RequestMethod.GET)
	public ModelAndView changeDraft(@RequestParam final int idProblem) {
		ModelAndView result;
		final Problem problem = this.problemService.findOne(idProblem);

		try {
			this.problemService.changeDraft(problem);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("problem.cannot.changeDraft");
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idProblem) {
		ModelAndView result;
		final Problem problem = this.problemService.findOne(idProblem);

		try {
			this.problemService.delete(problem);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("problem.cannot.delete");
		}
		return result;

	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("problem/list");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
		final Collection<Problem> problems = this.problemService.getProblemsOfCompany(company.getId());
		result.addObject("problems", problems);
		result.addObject("company", true);
		result.addObject("requestURI", "problem/company/list.do");
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idProblem) {
		final ModelAndView result;

		final Problem problem = this.problemService.findOne(idProblem);

		result = new ModelAndView("problem/display");

		result.addObject("problem", problem);

		this.configValues(result);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Problem problem) {
		return this.createEditModelAndView(problem, null);
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String message) {
		final ModelAndView result = new ModelAndView("problem/edit");

		final UserAccount principal = LoginService.getPrincipal();
		final Company company = this.companyService.findByPrincipal(principal.getId());
		final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());

		result.addObject("problem", problem);
		result.addObject("positions", positions);

		result.addObject("message", message);

		return result;
	}
}
