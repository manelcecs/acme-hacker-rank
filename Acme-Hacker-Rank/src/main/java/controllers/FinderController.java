
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;
import forms.SearchForm;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result;

		final SearchForm searchForm = new SearchForm();

		result = this.createEditModelAndView(searchForm);
		result.addObject("requestURI", "finder/search.do");
		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchForm searchForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(searchForm);
		else
			try {
				result = new ModelAndView("finder/search");
				final Collection<Position> positions = this.positionService.getFilterPositionsByKeyword(searchForm.getKeyword());
				result.addObject("positions", positions);
				result.addObject("requestURI", "finder/search.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(searchForm, "finder.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchForm searchForm) {

		final ModelAndView result;

		result = this.createEditModelAndView(searchForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchForm searchForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("finder/search");

		result.addObject("searchForm", searchForm);

		result.addObject("message", messageCode);

		return result;
	}
}
