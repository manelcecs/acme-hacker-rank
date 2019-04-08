
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("company/list");

		final Collection<Company> companies = this.companyService.findAll();

		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}

}
