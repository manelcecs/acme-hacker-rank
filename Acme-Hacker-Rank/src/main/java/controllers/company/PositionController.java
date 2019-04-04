
package controllers.company;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CompanyService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import forms.PositionForm;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final PositionForm positionForm = new PositionForm();
		return this.createEditModelAndView(positionForm);

	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
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
		final ModelAndView result = new ModelAndView("position/edit");

		result.addObject("positionForm", positionForm);
		result.addObject("message", message);

		return result;
	}

	//TODO: Las colecciones de position controlar que no están vacias. Ask toni y deivid
	@RequestMapping(value = "/company/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final PositionForm positionForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(positionForm);
		else
			try {
				final Position positionSave = this.positionService.reconstruct(positionForm, binding);
				this.positionService.save(positionSave);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.creadeEditModelAndView(positionForm, "cannot.save.position");
			}
		return result;
	}

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		//		final ModelAndView result = new ModelAndView("position/list");
		//
		//		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
		//
		//		final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());
		//
		//		result.addObject("positions", positions);
		//		result.addObject("owner", true);
		//		result.addObject("requestURI", "position/company/list.do");
		//
		//		return result;
		return this.listModelAndView(null);
	}

	@RequestMapping(value = "/company/changeDraft", method = RequestMethod.GET)
	public ModelAndView changeDraft(@RequestParam final int idPosition) {
		ModelAndView result;
		final Position position = this.positionService.findOne(idPosition);

		try {
			this.positionService.changeDraft(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			//			result = new ModelAndView("position/list");
			//			final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
			//			final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());
			//			result.addObject("positions", positions);
			//			result.addObject("owner", true);
			//			result.addObject("requestURI", "position/company/list.do");
			//			result.addObject("error", "cannot.change.draft");
			result = this.listModelAndView("position.cannot.changeDraft");
		}
		return result;

	}

	@RequestMapping(value = "/company/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idPosition) {
		ModelAndView result;
		final Position position = this.positionService.findOne(idPosition);

		try {
			this.positionService.changeDraft(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			//			result = new ModelAndView("position/list");
			//			final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
			//			final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());
			//			result.addObject("positions", positions);
			//			result.addObject("owner", true);
			//			result.addObject("requestURI", "position/company/list.do");
			//			result.addObject("error", "cannot.change.draft");
			result = this.listModelAndView("position.cannot.delete");
		}
		return result;

	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("position/list");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
		final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());
		result.addObject("positions", positions);
		result.addObject("owner", true);
		result.addObject("requestURI", "position/company/list.do");
		result.addObject("message", message);
		return null;
	}

}
