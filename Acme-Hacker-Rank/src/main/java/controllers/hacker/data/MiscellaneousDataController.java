
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
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.MiscellaneousData;

@Controller
@RequestMapping("/miscellaneousData/hacker")
public class MiscellaneousDataController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private MiscellaneousDataService	miscService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		final ModelAndView res;
		final MiscellaneousData miscellaneousData = this.miscService.findOne(miscellaneousDataId);
		res = this.createModelAndViewEdit(miscellaneousData);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final MiscellaneousData miscellaneousData = this.miscService.create(curriculaId);
		res = this.createModelAndViewEdit(miscellaneousData);

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer id) {

		final int curriculaId;
		ModelAndView res;
		final MiscellaneousData record = this.miscService.findOne(id);
		curriculaId = record.getCurricula().getId();
		try {
			this.miscService.delete(record);
		} catch (final Throwable oops) {
			oops.printStackTrace();
		}

		res = this.createModelAndViewCurricula(curriculaId);

		return res;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final MiscellaneousData miscellaneousData, final BindingResult binding) {

		ModelAndView res;
		if (binding.hasErrors())
			res = this.createModelAndViewEdit(miscellaneousData);
		else
			try {
				this.miscService.save(miscellaneousData);
				res = this.createModelAndViewCurricula(miscellaneousData.getCurricula().getId());
			} catch (final ValidationException oops) {
				System.out.println("Validation Exception");
				System.out.println(binding.getAllErrors());
				res = this.createModelAndViewEdit(miscellaneousData);
			} catch (final Throwable oops) {
				System.out.println("Generic Exception");
				oops.printStackTrace();
				res = this.createModelAndViewEdit(miscellaneousData);
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

	protected ModelAndView createModelAndViewEdit(final MiscellaneousData miscData) {
		ModelAndView res;

		res = new ModelAndView("miscellaneousData/edit");
		res.addObject("miscellaneousData", miscData);
		this.configValues(res);
		return res;
	}

}
