
package controllers.administrator;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminConfigService;
import controllers.AbstractController;
import domain.AdminConfig;
import forms.AdminConfigForm;

@Controller
@RequestMapping("/adminConfig/administrator")
public class AdminConfigController extends AbstractController {

	@Autowired
	private AdminConfigService	adminConfigService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("adminConfig/display");

		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		result.addObject("adminConfig", adminConfig);

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final AdminConfigForm adminConfigForm = this.adminConfigService.getAdminConfig().castToForm();
		result = this.createModelAndView(adminConfigForm);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AdminConfigForm adminConfigForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("adminConfig/edit");

		try {
			final AdminConfig adminConfig = this.adminConfigService.reconstruct(adminConfigForm, binding);
			this.adminConfigService.save(adminConfig);
			result = new ModelAndView("redirect:display.do");
		} catch (final ValidationException oops) {
			result = this.createModelAndView(adminConfigForm);
		} catch (final Throwable oops) {
			result = this.createModelAndView(adminConfigForm, "adminConfig.save.error");
		}

		return result;
	}

	@RequestMapping(value = "/deleteSpamWord", method = RequestMethod.POST)
	public ModelAndView deleteSpamWord(final String spamWord) {
		ModelAndView result;

		try {
			this.adminConfigService.deleteSpamWord(spamWord);
			result = new ModelAndView("redirect:edit.do");
		} catch (final Throwable oops) {
			result = this.createModelAndView(this.adminConfigService.getAdminConfig().castToForm(), "adminConfig.save.error");
		}

		this.configValues(result);
		return result;
	}

	protected ModelAndView createModelAndView(final AdminConfigForm adminConfigForm) {
		return this.createModelAndView(adminConfigForm, null);
	}

	private ModelAndView createModelAndView(final AdminConfigForm adminConfigForm, final String message) {
		final ModelAndView result = new ModelAndView("adminConfig/edit");
		final List<String> spamWords = (List<String>) this.adminConfigService.getAdminConfig().getSpamWords();

		result.addObject("adminConfigForm", adminConfigForm);
		result.addObject("requestURI", "adminConfig/administrator/edit.do");
		result.addObject("spamWords", spamWords);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}
}
