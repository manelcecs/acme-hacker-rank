/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccountRepository;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/administrator/register")
	public ModelAndView register() {
		ModelAndView res;

		final AdministratorForm adminForm = new AdministratorForm();

		res = this.createModelAndViewEdit(adminForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final AdministratorForm adminForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Administrator adminRect = this.adminService.reconstruct(adminForm, binding);
			this.adminService.save(adminRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(adminForm);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(adminForm, "administrator.edit.commit.error");
			oops.printStackTrace();
		}

		return res;

	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveAdmin(final Administrator administrator, final BindingResult binding) {
		ModelAndView res;

		if (!this.adminService.validateEmail(administrator.getEmail()))
			binding.rejectValue("email", "error.email");
		try {
			final Administrator adminRect = this.adminService.reconstruct(administrator, binding);
			this.adminService.save(adminRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(administrator);

		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(administrator);

		}
		return res;
	}

	/*
	 * 
	 * **CREATEmodelANDview's
	 */

	protected ModelAndView createModelAndViewEdit(final AdministratorForm adminForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administratorForm", adminForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		//TODO: añadir el banner y el sysname
		//this.configValues(res);

		return result;

	}

	protected ModelAndView createModelAndViewEdit(final Administrator admin, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		//TODO: añadir el banner y el sysname
		//this.configValues(res);

		return result;

	}

}
