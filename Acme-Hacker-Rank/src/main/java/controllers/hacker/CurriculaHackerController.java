
package controllers.hacker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaHackerController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private HackerService				hackerService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private PositionDataService			positionDataService;
	@Autowired
	private EducationDataService		educationDataService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		res = this.createModelAndViewList();
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Integer curriculaId) {
		ModelAndView res;
		res = this.createModelAndViewDisplay(curriculaId);
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Curricula curricula = this.curriculaService.save(this.curriculaService.create());
		res = this.createModelAndViewEditPersonal(curricula.getId());
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final Integer curriculaId) {
		ModelAndView res;
		res = this.createModelAndViewList();
		return res;
	}

	protected ModelAndView createModelAndViewEditPersonal(final Integer curriculaId) {
		final ModelAndView res = new ModelAndView("personalData/edit");
		final PersonalData personalData = this.personalDataService.create(curriculaId);
		res.addObject("personalData", personalData);
		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewDisplay(final Integer curriculaId) {
		ModelAndView res = new ModelAndView("curricula/display");
		try {
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

		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		this.configValues(res);
		return res;
	}
	protected ModelAndView createModelAndViewList() {
		ModelAndView res = new ModelAndView("curricula/list");

		try {
			final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
			final List<Curricula> curriculas = (List<Curricula>) this.curriculaService.findAllNoCopy(hacker);
			res.addObject("curriculas", curriculas);
			res.addObject("show", true);

		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		this.configValues(res);

		return res;
	}

}
