
package controllers.hacker;

import java.text.ParseException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.FinderService;
import services.HackerService;
import controllers.AbstractController;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/finder/hacker")
public class FinderHackerController extends AbstractController {

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private FinderService	finderService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() throws ParseException {
		final ModelAndView result;

		final UserAccount principal = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.findByPrincipal(principal);

		final Finder finder = hacker.getFinder();

		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		result.addObject("requestURI", "finder/hacker/edit.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) throws ParseException {
		ModelAndView result;

		final Finder finderBD = this.hackerService.findByPrincipal(LoginService.getPrincipal()).getFinder();

		finder = this.finderService.reconstruct(finder, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				if (!this.finderService.cacheFinder(finderBD, finder))
					this.finderService.save(finder);

				result = new ModelAndView("redirect:edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid Finder finder, final BindingResult binding) throws ParseException {
		ModelAndView result;

		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		finder = hacker.getFinder();
		finder.setKeyWord(null);
		finder.setDeadline(null);
		finder.setMaximumDeadLine(null);
		finder.setMinimumSalary(null);
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {

		final ModelAndView result;

		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("finder/edit");

		final Collection<Position> positions = finder.getPositions();
		result.addObject("finder", finder);
		result.addObject("positions", positions);

		result.addObject("message", messageCode);

		return result;
	}
}
