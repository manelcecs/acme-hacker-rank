
package controllers;

import java.util.List;

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
import services.MessageService;
import services.SocialProfileService;
import utiles.AuthorityMethods;

import com.fasterxml.jackson.core.JsonProcessingException;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Message;
import domain.SocialProfile;

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

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		result = this.createModelAndViewDisplay();

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView res;

		res = this.createModelAndViewEditActor();

		return res;

	}

	protected ModelAndView createModelAndViewDisplay() {
		final ModelAndView result = new ModelAndView("actor/display");

		final UserAccount principal = LoginService.getPrincipal();

		final Actor actor = this.actorService.findByUserAccount(principal);

		result.addObject("actor", actor);
		result.addObject("userLogged", principal);

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		result.addObject("authority", authority.getAuthority());

		final List<SocialProfile> socialProfiles = (List<SocialProfile>) this.socialProfileService.findAllSocialProfiles();
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
		}

		result.addObject("socialProfiles", socialProfiles);
		this.configValues(result);
		return result;

	}
	protected ModelAndView createModelAndViewEditActor() {
		ModelAndView result;

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator admin = this.administratorService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("administrator/edit");
			result.addObject("administrator", admin);
			result.addObject("edit", true);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("hacker/edit");
			result.addObject("hacker", hacker);
			result.addObject("edit", true);
			break;

		case "COMPANY":
			final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("company/edit");
			result.addObject("company", company);
			result.addObject("edit", true);
			break;
		default:

			result = new ModelAndView("display.do");
			break;
		}

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/displayData", method = RequestMethod.GET)
	public ModelAndView displayData() {
		final ModelAndView result = new ModelAndView("actor/displayData");
		List<Message> messages;
		final List<SocialProfile> socialProfiles = (List<SocialProfile>) this.socialProfileService.findAllSocialProfiles();

		final UserAccount principal = LoginService.getPrincipal();
		String authority = AuthorityMethods.getLoggedAuthority().getAuthority();

		if (authority.equals("BAN"))
			authority = this.actorService.checkAuthorityIsBanned(principal);

		result.addObject("authority", authority);

		switch (authority) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(administrator.getId());
			result.addObject("administrator", administrator);
			result.addObject("messages", messages);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(hacker.getId());
			result.addObject("hacker", hacker);
			result.addObject("messages", messages);
			break;

		case "COMPANY":
			final Company company = this.companyService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(company.getId());
			result.addObject("company", company);
			result.addObject("messages", messages);

			break;
		}
		result.addObject("socialProfiles", socialProfiles);

		this.configValues(result);
		return result;

	}
	@RequestMapping(value = "/saveData", method = RequestMethod.GET)
	public ModelAndView fileJSON() throws JsonProcessingException {
		final ModelAndView result = new ModelAndView("actor/exportData");

		final String json = this.actorService.exportData();

		final UserAccount principal = LoginService.getPrincipal();
		final List<Authority> authorities = (List<Authority>) principal.getAuthorities();
		final String authority = authorities.get(0).getAuthority();

		result.addObject("authority", authority);
		result.addObject("json", json);

		this.configValues(result);
		return result;

	}

	@RequestMapping(value = "/deleteData", method = RequestMethod.GET)
	public ModelAndView deleteAllData() {
		ModelAndView result;
		try {
			this.actorService.deleteData();
			result = new ModelAndView("redirect:../j_spring_security_logout");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/displayData.do");
		}

		this.configValues(result);
		return result;
	}

}
