
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int socialProfileId) {
		ModelAndView res;

		res = this.createModelAndViewEdit(socialProfileId);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		res = this.createModelAndViewCreate();

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createModelAndViewEdit(socialProfile);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = this.createModelAndViewEdit(socialProfile);
			}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView save(final int socialProfileId) {
		ModelAndView res;

		try {
			final SocialProfile sp = this.socialProfileService.findOne(socialProfileId);
			this.socialProfileService.delete(sp);
		} catch (final Throwable oops) {
		}
		res = new ModelAndView("redirect:/actor/display.do");
		return res;
	}

	protected ModelAndView createModelAndViewCreate() {
		ModelAndView res;

		final SocialProfile socialProfile = this.socialProfileService.create();

		res = new ModelAndView("socialProfile/edit");
		res.addObject("socialProfile", socialProfile);

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndViewEdit(final int socialProfileId) {
		ModelAndView res;

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);

		Assert.isTrue(socialProfile.getActor().getId() == this.actorService.findByUserAccount(LoginService.getPrincipal()).getId());

		res = new ModelAndView("socialProfile/edit");
		res.addObject("socialProfile", socialProfile);
		this.configValues(res);

		return res;
	}
	protected ModelAndView createModelAndViewEdit(final SocialProfile socialProfile) {
		ModelAndView res;

		Assert.isTrue(socialProfile.getActor().getId() == this.actorService.findByUserAccount(LoginService.getPrincipal()).getId());

		res = new ModelAndView("socialProfile/edit");
		res.addObject("socialProfile", socialProfile);
		this.configValues(res);

		return res;
	}

}
