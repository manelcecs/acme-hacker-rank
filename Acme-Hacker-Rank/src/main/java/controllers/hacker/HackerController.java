
package controllers.hacker;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HackerService;
import controllers.AbstractController;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService	hackerService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final HackerForm hackerForm = new HackerForm();

		res = this.createModelAndViewEdit(hackerForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final HackerForm hackerForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Hacker hackerRect = this.hackerService.reconstruct(hackerForm, binding);
			this.hackerService.save(hackerRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(hackerForm);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(hackerForm, "hacker.edit.commit.error");
			oops.printStackTrace();
		}

		return res;

	}

	@RequestMapping(value = "/hacker/save", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveAdmin(final Hacker hacker, final BindingResult binding) {
		ModelAndView res;

		try {
			final Hacker hackerRect = this.hackerService.reconstruct(hacker, binding);
			this.hackerService.save(hackerRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(hacker);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(hacker);
			oops.printStackTrace();
		}
		return res;
	}

	/*
	 * MODELANDVIEWS METHODS
	 */

	protected ModelAndView createModelAndViewEdit(final HackerForm hackerForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("hackerForm", hackerForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createModelAndViewEdit(final Hacker hacker, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
