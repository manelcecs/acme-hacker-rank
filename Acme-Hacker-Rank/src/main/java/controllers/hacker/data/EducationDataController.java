
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
import services.EducationDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationData;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataController extends AbstractController {

	@Autowired
	private EducationDataService	educationDataService;
	@Autowired
	private CurriculaService		curriculaService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		final ModelAndView res;
		final EducationData educationData = this.educationDataService.findOne(educationDataId);
		res = this.createModelAndViewEdit(educationData);

		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final EducationData educationData = this.educationDataService.create(curriculaId);
		res = this.createModelAndViewEdit(educationData);
		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int educationDataId) {
		final int curriculaId;
		ModelAndView res;
		final EducationData record = this.educationDataService.findOne(educationDataId);
		curriculaId = record.getCurricula().getId();
		try {
			this.educationDataService.delete(record);
		} catch (final Throwable oops) {
			oops.printStackTrace();
		}

		res = this.createModelAndViewCurricula(curriculaId);

		return res;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final EducationData educationData, final BindingResult binding) {

		ModelAndView res;
		if (binding.hasErrors())
			res = this.createModelAndViewEdit(educationData);
		else
			try {
				this.educationDataService.save(educationData);
				res = this.createModelAndViewCurricula(educationData.getCurricula().getId());

			} catch (final ValidationException oops) {
				System.out.println("Validation Exception");
				System.out.println(binding.getAllErrors());
				res = this.createModelAndViewEdit(educationData);
			} catch (final Throwable oops) {
				System.out.println("Generic Exception");
				oops.printStackTrace();
				res = this.createModelAndViewEdit(educationData);
			}

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewEdit(final EducationData educationData) {
		ModelAndView res;
		res = new ModelAndView("educationData/edit");
		res.addObject("educationData", educationData);
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

}
