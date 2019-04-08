
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.HackerService;
import utiles.AuthorityMethods;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private HackerService			hackerService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result = new ModelAndView("actor/display");

		final UserAccount principal = LoginService.getPrincipal();

		final Actor actor = this.actorService.findByUserAccount(principal);

		result.addObject("actor", actor);
		result.addObject("userLogged", principal);

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		result.addObject("authority", authority.getAuthority());
		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findOne(actor.getId());
			result.addObject("administrator", administrator);
			System.out.println(administrator);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findOne(actor.getId());
			System.out.println(hacker);
			result.addObject("hacker", hacker);
			break;

		case "COMPANY":
			final Company company = this.companyService.findOne(actor.getId());
			System.out.println(company);
			result.addObject("company", company);
			break;
		default:
			result = new ModelAndView("display.do");
			break;
		}

		//TODO: this.configValues(result);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView res;

		res = this.createModelAndViewEditActor();

		return res;

	}

	protected ModelAndView createModelAndViewEditActor() {

		ModelAndView res;

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator admin = this.administratorService.findByPrincipal(LoginService.getPrincipal());
			res = new ModelAndView("administrator/edit");
			res.addObject("administrator", admin);
			res.addObject("edit", true);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
			System.out.println(hacker);
			res = new ModelAndView("hacker/edit");
			res.addObject("hacker", hacker);
			res.addObject("edit", true);
			break;

		case "COMPANY":
			final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());
			System.out.println(company);
			res = new ModelAndView("company/edit");
			res.addObject("company", company);
			res.addObject("edit", true);
			break;
		default:
			res = new ModelAndView("display.do");
			break;
		}

		return res;
	}

}
