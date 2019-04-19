
package controllers.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import controllers.AbstractController;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final CompanyForm companyForm = new CompanyForm();

		res = this.createModelAndViewEdit(companyForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final CompanyForm companyForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Company companyRect = this.companyService.reconstruct(companyForm, binding);
			this.companyService.save(companyRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(companyForm);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(companyForm, "company.edit.commit.error");

		}

		return res;

	}

	@RequestMapping(value = "/company/save", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveAdmin(final Company company, final BindingResult binding) {
		ModelAndView res;

		try {
			final Company companyRect = this.companyService.reconstruct(company, binding);
			this.companyService.save(companyRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(company);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(company);

		}
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("company/list");

		final Collection<Company> companies = this.companyService.findAll();

		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		this.configValues(result);
		return result;
	}

	/*
	 * MODELANDVIEWS METHODS
	 */

	protected ModelAndView createModelAndViewEdit(final CompanyForm companyForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("companyForm", companyForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createModelAndViewEdit(final Company company, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("company", company);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
