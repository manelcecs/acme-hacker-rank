
package controllers.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
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
@RequestMapping("/curricula/company")
public class CurriculaCompanyController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private PositionDataService			positionDataService;
	@Autowired
	private EducationDataService		educationDataService;


	@RequestMapping("/list")
	public ModelAndView list(final Integer applicationId) {
		ModelAndView res;
		res = this.createModelAndViewList(applicationId);
		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(final Integer curriculaId) {
		ModelAndView res;
		res = this.createModelAndViewDisplay(curriculaId);
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

			res.addObject("positionData", positionData);
			res.addObject("educationsData", educationsData);
			res.addObject("personalData", personalData);
			res.addObject("miscellaneousData", miscellaneousData);

			res.addObject("show", false);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		this.configValues(res);
		return res;
	}
	protected ModelAndView createModelAndViewList(final Integer applicationId) {
		ModelAndView res = new ModelAndView("curricula/list");

		try {
			final List<Curricula> curriculas = (List<Curricula>) this.curriculaService.findAllApplication(applicationId);
			res.addObject("curriculas", curriculas);
			res.addObject("show", false);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		this.configValues(res);

		return res;
	}
}
