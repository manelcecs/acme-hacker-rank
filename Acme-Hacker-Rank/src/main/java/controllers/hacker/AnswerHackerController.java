
package controllers.hacker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import controllers.AbstractController;
import domain.Answer;

@Controller
@RequestMapping("/answer/hacker")
public class AnswerHackerController extends AbstractController {

	@Autowired
	AnswerService	answerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int idApplication) {
		final Answer answer = this.answerService.create(idApplication);
		return this.createModelAndView(answer);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Answer answer, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createModelAndView(answer);
		else
			try {
				this.answerService.save(answer);
				result = new ModelAndView("redirect:/application/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(answer, "cannot.save.answer");
			}
		return result;
	}

	protected ModelAndView createModelAndView(final Answer answer) {
		return this.createModelAndView(answer, null);
	}

	private ModelAndView createModelAndView(final Answer answer, final String message) {
		final ModelAndView result = new ModelAndView("answer/create");

		result.addObject("answer", answer);
		result.addObject("message", message);

		return result;
	}

}
