
package controllers.hacker.data;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.PositionData;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PositionDataService	positionDataService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer personalDataId) {
		final ModelAndView res;
		final PositionData positionData = this.positionDataService.findOne(personalDataId);
		res = this.createModelAndViewEdit(positionData);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer curriculaId) {
		final ModelAndView res;
		final PositionData positionData = this.positionDataService.create(curriculaId);
		res = this.createModelAndViewEdit(positionData);

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer personalDataId) {

		final int curriculaId;
		ModelAndView res;
		final PositionData record = this.positionDataService.findOne(personalDataId);
		curriculaId = record.getCurricula().getId();
		try {
			this.positionDataService.delete(record);
		} catch (final Throwable oops) {
			oops.printStackTrace();
		}

		res = this.createModelAndViewCurricula(curriculaId);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final PositionData positionData, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createModelAndViewEdit(positionData);
		else
			try {
				this.positionDataService.save(positionData);
				res = this.createModelAndViewCurricula(positionData.getCurricula().getId());
			} catch (final ValidationException oops) {
				System.out.println("Validation Exception");
				System.out.println(binding.getAllErrors());
				res = this.createModelAndViewEdit(positionData);
			} catch (final Throwable oops) {
				System.out.println("Generic Exception");
				oops.printStackTrace();
				res = this.createModelAndViewEdit(positionData);
			}

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewCurricula(final int curriculaId) {
		ModelAndView res;
		res = new ModelAndView("curricula/display");
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		res.addObject("curricula", curricula);
		res.addObject("show", true);
		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewEdit(final PositionData positionData) {
		ModelAndView res;

		res = new ModelAndView("positionData/edit");
		res.addObject("positionData", positionData);
		this.configValues(res);

		return res;
	}

}
