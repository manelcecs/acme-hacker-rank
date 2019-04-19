
package controllers.hacker.data;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculaService;
import services.EducationDataService;
import services.HackerService;
import services.MiscellaneousDataService;
import services.PersonalDataService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Controller
@RequestMapping("/personalData/hacker")
public class PersonalDataController extends AbstractController {

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private HackerService				hackerService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView res;
		final PersonalData personalData = this.personalDataService.findOne(personalDataId);

		if (personalData.getCurricula().getCopy())
			res = this.createModelAndViewCurricula(personalData.getCurricula().getId());
		else
			res = this.createModelAndViewEdit(personalData);
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView res;
		final PersonalData personalData = this.personalDataService.create(curriculaId);
		res = this.createModelAndViewEdit(personalData);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final PersonalData personalData, final BindingResult binding) {

		ModelAndView res;

		try {
			final PersonalData result = this.personalDataService.reconstruct(personalData, binding);

			this.personalDataService.save(result);
			res = this.createModelAndViewCurricula(result.getCurricula().getId());
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(personalData);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(personalData, "personalData.submit.error");
		}

		return res;

	}

	protected ModelAndView createModelAndViewEdit(final PersonalData personalData, final String... msg) {
		final ModelAndView res;

		if (this.hackerService.findByPrincipal(LoginService.getPrincipal()).getId() != personalData.getCurricula().getHacker().getId())
			res = new ModelAndView("redirect:/");
		else {
			res = new ModelAndView("personalData/edit");
			res.addObject("personalData", personalData);

			for (final String s : msg)
				res.addObject("message", s);
		}

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewCurricula(final int curriculaId) {
		ModelAndView res;
		res = new ModelAndView("curricula/display");
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		res.addObject("curricula", curricula);

		final List<MiscellaneousData> miscellaneousData = (List<MiscellaneousData>) this.miscellaneousDataService.findAllCurricula(curricula);
		final List<PositionData> positionData = (List<PositionData>) this.positionDataService.findAllCurricula(curricula);
		final List<EducationData> educationsData = (List<EducationData>) this.educationDataService.findAllCurricula(curricula);
		final PersonalData personalData = this.personalDataService.findByCurricula(curricula);

		res.addObject("positionsData", positionData);
		res.addObject("educationsData", educationsData);
		res.addObject("personalData", personalData);
		res.addObject("miscellaneousData", miscellaneousData);

		res.addObject("show", true);
		this.configValues(res);
		return res;
	}

}
