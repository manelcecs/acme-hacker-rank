
package controllers.hacker;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
import forms.CurriculaAndPersonalDataForm;

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
		final CurriculaAndPersonalDataForm curriculaPersonalForm = new CurriculaAndPersonalDataForm();
		res = this.createModelAndViewEdit(curriculaPersonalForm);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final CurriculaAndPersonalDataForm curriculaPersonalForm, final BindingResult binding) {
		ModelAndView res;
		Curricula curricula = null;
		Curricula saved = null;
		PersonalData personalData;
		try {
			curricula = this.curriculaService.reconstruct(curriculaPersonalForm, binding);
			saved = this.curriculaService.save(curricula);

			personalData = this.personalDataService.reconstruct(curriculaPersonalForm, saved.getId(), binding);
			this.personalDataService.save(personalData);

			res = this.createModelAndViewDisplay(saved.getId());
		} catch (final ValidationException oops) {
			if (saved != null && saved.getId() != 0)
				this.curriculaService.delete(saved);//Por si falla el save/reconstruct de PersonalData

			res = this.createModelAndViewEdit(curriculaPersonalForm);
		} catch (final Throwable oops) {
			if (saved != null && saved.getId() != 0)
				this.curriculaService.delete(saved);//Por si falla el save/reconstruct de PersonalData
			res = this.createModelAndViewEdit(curriculaPersonalForm, "curricula.submit.error");
		}

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final Integer curriculaId) {
		ModelAndView res;
		this.curriculaService.delete(this.curriculaService.findOne(curriculaId));
		res = this.createModelAndViewList();
		return res;
	}

	protected ModelAndView createModelAndViewEdit(final CurriculaAndPersonalDataForm curriculaPersonalForm, final String... msg) {
		final ModelAndView res = new ModelAndView("curricula/edit");

		res.addObject("curriculaAndPersonalDataForm", curriculaPersonalForm);
		for (final String s : msg)
			res.addObject("message", s);

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewDisplay(final Integer curriculaId) {
		ModelAndView res = new ModelAndView("curricula/display");
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);

			Assert.isTrue(this.hackerService.findByPrincipal(LoginService.getPrincipal()).getId() == curricula.getHacker().getId());

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
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		this.configValues(res);

		return res;
	}

}
