
package controllers.hacker.data;

import java.util.List;

import javax.validation.Valid;
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
@RequestMapping("/positionData/hacker")
public class PositionDataController extends AbstractController {

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
	public ModelAndView edit(@RequestParam final Integer positionDataId) {
		final ModelAndView res;
		final PositionData positionData = this.positionDataService.findOne(positionDataId);

		if (positionData.getCurricula().getCopy())
			res = this.createModelAndViewCurricula(positionData.getCurricula().getId());
		else
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
	public ModelAndView delete(@RequestParam final Integer positionDataId) {

		final int curriculaId;
		ModelAndView res;
		final PositionData record = this.positionDataService.findOne(positionDataId);
		curriculaId = record.getCurricula().getId();
		try {
			this.positionDataService.delete(record);
			res = this.createModelAndViewCurricula(curriculaId);
		} catch (final Throwable oops) {
			res = this.createModelAndViewCurricula(curriculaId, "positionData.submit.error");
		}

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final PositionData positionData, final BindingResult binding) {

		ModelAndView res;

		if (positionData.getEndDate() != null)
			if (positionData.getEndDate().before(positionData.getStartDate()))
				binding.rejectValue("endDate", "positionData.edit.date.error");

		if (binding.hasErrors())
			res = this.createModelAndViewEdit(positionData);
		else
			try {
				this.positionDataService.save(positionData);
				res = this.createModelAndViewCurricula(positionData.getCurricula().getId());
			} catch (final ValidationException oops) {
				res = this.createModelAndViewEdit(positionData);
			} catch (final Throwable oops) {
				res = this.createModelAndViewEdit(positionData);
			}

		return res;
	}

	protected ModelAndView createModelAndViewCurricula(final int curriculaId, final String... message) {
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

		for (final String s : message)
			res.addObject("message", s);

		res.addObject("show", true);
		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewEdit(final PositionData positionData) {
		ModelAndView res;

		if (this.hackerService.findByPrincipal(LoginService.getPrincipal()).getId() != positionData.getCurricula().getHacker().getId())
			res = new ModelAndView("redirect:/");
		else {
			res = new ModelAndView("positionData/edit");
			res.addObject("positionData", positionData);
		}

		this.configValues(res);
		return res;
	}
}
