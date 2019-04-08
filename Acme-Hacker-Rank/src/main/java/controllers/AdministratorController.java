
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public ModelAndView process() {
		final ModelAndView result = new ModelAndView("administrator/process");
		result.addObject("spamActors", this.actorService.getSpammerActors());
		result.addObject("actorLogged", LoginService.getPrincipal());
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/updateSpam", method = RequestMethod.GET)
	public ModelAndView updateSpam() {
		final ModelAndView result = new ModelAndView("redirect:process.do");
		try {
			this.actorService.updateSpam();
		} catch (final Throwable oops) {
			result.addObject("message", "administrator.commit.error");
		}
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final Integer idActor) {
		final ModelAndView result = new ModelAndView("redirect:process.do");
		try {
			final Actor actor = this.actorService.getActor(idActor);
			this.actorService.ban(actor);
		} catch (final Throwable oops) {
			result.addObject("message", "administrator.commit.error");
		}
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final Integer idActor) {
		final ModelAndView result = new ModelAndView("redirect:process.do");
		try {
			final Actor actor = this.actorService.getActor(idActor);
			this.actorService.unban(actor);
		} catch (final Throwable oops) {
			result.addObject("message", "administrator.commit.error");
		}
		this.configValues(result);
		return result;
	}

}
