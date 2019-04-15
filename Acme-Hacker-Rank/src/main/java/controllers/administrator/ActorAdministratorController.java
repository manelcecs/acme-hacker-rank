
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.HackerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;

@Controller
@RequestMapping("/actor")
public class ActorAdministratorController extends AbstractController {

	@Autowired
	private HackerService			hackerService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/administrator/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idActor) {

		ModelAndView result;

		result = this.createModelAndViewDisplayByAdmin(idActor);

		return result;

	}

	protected ModelAndView createModelAndViewDisplayByAdmin(final int idActor) {

		ModelAndView result = new ModelAndView("actor/display");

		final UserAccount principal = LoginService.getPrincipal();
		result.addObject("userLogged", principal);

		final Authority authority = this.actorService.getActor(idActor).getUserAccount().getAuthorities().iterator().next();

		result.addObject("authority", authority.getAuthority());
		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findOne(idActor);
			result.addObject("administrator", administrator);
			System.out.println(administrator);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findOne(idActor);
			System.out.println(hacker);
			result.addObject("hacker", hacker);
			break;

		case "COMPANY":
			final Company company = this.companyService.findOne(idActor);
			System.out.println(company);
			result.addObject("company", company);
			break;
		default:
			result = new ModelAndView("display.do");
			break;
		}

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/administrator/listActors", method = RequestMethod.GET)
	public ModelAndView listActors() {
		final ModelAndView res = new ModelAndView("administrator/listActors");

		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<Actor> actorsRemoved = this.actorService.findEliminatedActors();
		final Collection<Administrator> administrators = this.administratorService.findAll();
		actors.removeAll(actorsRemoved);
		actors.removeAll(administrators);

		res.addObject("actors", actors);
		res.addObject("requestURI", "actor/administrator/listActors.do");

		this.configValues(res);
		return res;

	}

}
