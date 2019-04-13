
package controllers.hacker.data;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculaService;
import services.HackerService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Controller
@RequestMapping("/personalData/hacker")
public class PersonalDataController extends AbstractController {

	@Autowired
	private PersonalDataService	personalDataService;
	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private HackerService		hackerService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView res;

		try {

			final PersonalData pd = this.personalDataService.findOne(personalDataId);

			res = this.createModelAndViewEdit(pd);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = this.createModelAndViewListCurricula();
		}

		this.configValues(res);
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
	public ModelAndView save(@Valid final PersonalData personalData, final BindingResult binding) {

		ModelAndView res;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res = this.createModelAndViewEdit(personalData);
		} else {
			try {

				this.personalDataService.save(personalData);

			} catch (final Throwable oops) {
				oops.printStackTrace();
			}
			res = this.createModelAndViewListCurricula();
		}

		this.configValues(res);
		return res;

	}

	protected ModelAndView createModelAndViewEdit(final PersonalData personalData) {
		final ModelAndView res = new ModelAndView("personalData/edit");
		res.addObject("personalData", personalData);
		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewListCurricula() {
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
