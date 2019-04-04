
package controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CompanyService;
import services.PositionService;
import domain.Company;
import domain.Position;
import forms.PositionForm;

@Controller
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final PositionForm positionForm = new PositionForm();
		return this.createEditModelAndView(positionForm);

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idPosition) {
		ModelAndView result;

		final Position position = this.positionService.findOne(idPosition);

		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());

		if (!position.getCompany().equals(company))
			result = new ModelAndView("redirect:list.do");
		else
			result = this.createEditModelAndView(this.positionService.castToForm(position));

		return result;

	}

	protected ModelAndView createEditModelAndView(final PositionForm positionForm) {
		return this.creadeEditModelAndView(positionForm, null);
	}

	private ModelAndView creadeEditModelAndView(final PositionForm positionForm, final String message) {
		final ModelAndView result = new ModelAndView("position/create");

		result.addObject("positionForm", positionForm);
		result.addObject("message", message);

		return result;
	}

}
