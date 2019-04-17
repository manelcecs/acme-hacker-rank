
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import services.ProblemService;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer idCompany) {
		final ModelAndView result = new ModelAndView("position/list");
		Collection<Position> positions;

		if (idCompany == null)
			positions = this.positionService.getAllPositionsFiltered();
		else
			positions = this.positionService.getAllPositionsFilteredOfCompany(idCompany);

		result.addObject("positions", positions);
		result.addObject("viewAll", true);
		result.addObject("requestURI", "position/list.do");

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idPosition) {
		ModelAndView result;

		final Position position = this.positionService.findOne(idPosition);

		if (position.getDraft() || position.getCancelled())
			result = new ModelAndView("redirect:list.do");
		else {
			result = new ModelAndView("position/display");
			result.addObject("position", position);
			final Collection<Problem> problems = this.problemService.getProblemsOfPositionFinal(position.getId());
			result.addObject("problems", problems);
			result.addObject("requestURI", "position/display.do?idPosition=" + idPosition);
		}

		this.configValues(result);
		return result;
	}
}
