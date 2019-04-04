/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView res;

		final AdministratorForm adminForm = new AdministratorForm();

		res = this.createModelAndViewRegister(adminForm);

		return res;
	}

	@RequestMapping("/save")
	public ModelAndView save(@Valid final AdministratorForm adminForm, final BindingResult binding) {

		ModelAndView res;

		if (!this.adminService.validateEmail(adminForm.getEmail()))
			binding.rejectValue("email", "error.email");
		if (!adminForm.getUserAccount().getPassword().equals(adminForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "error.confirm.password");
		if (this.userAccountRepository.findByUsername(adminForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "error.unique.username");
		if (!adminForm.getTermsAndConditions())
			binding.rejectValue("termsConditions", "error.terms.conditions");

		try {
			final Administrator adminRect = this.adminService.reconstruct(adminForm, binding);
			this.adminService.save(adminRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(adminForm);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(adminForm, "admin.commit.error");
		}

		return res;

	}

	/*
	 * 
	 * **CREATEmodelANDview's
	 */

	protected ModelAndView createModelAndViewRegister(final AdministratorForm adminForm) {

		final ModelAndView res = new ModelAndView("administrator/register");

		res.addObject("administratorForm", adminForm);

		//TODO: añadir el banner y el sysname
		//this.configValues(res);

		return res;

	}

	protected ModelAndView createModelAndViewEdit(final AdministratorForm adminForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("administrator/register");
		result.addObject("administratorForm", adminForm);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		//TODO: añadir el banner y el sysname
		//this.configValues(res);

		return result;

	}

}
