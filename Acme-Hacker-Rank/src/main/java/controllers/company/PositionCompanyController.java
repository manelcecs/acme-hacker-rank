
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
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

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
		final ModelAndView result = new ModelAndView("position/edit");

		result.addObject("positionForm", positionForm);
		result.addObject("message", message);

		return result;
	}

	//TODO: Las colecciones de position controlar que no están vacias. Ask toni y deivid
	//TODO: Meter en el reconstruct si es 0 la id, que la fecha sea futuro. Comprobar que en modo draft sea una fecha futura
	@RequestMapping(value = "/save", method = RequestMethod.POST)
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView(null);
	}

	@RequestMapping(value = "/changeDraft", method = RequestMethod.GET)
	public ModelAndView changeDraft(@RequestParam final int idPosition) {
		ModelAndView result;
		final Position position = this.positionService.findOne(idPosition);

		try {
			this.positionService.changeDraft(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("position.cannot.changeDraft");
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idPosition) {
		ModelAndView result;
		final Position position = this.positionService.findOne(idPosition);

		try {
			this.positionService.delete(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("position.cannot.delete");
		}
		return result;

	}

	@RequestMapping(value = "/changeCancellation", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int idPosition) {
		ModelAndView result;
		final Position position = this.positionService.findOne(idPosition);

		try {
			this.positionService.changeCancellation(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("position.cannot.cancel");
		}
		return result;

	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("position/list");
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());
		final Collection<Position> positions = this.positionService.getPositionsOfCompany(company.getId());
		final Collection<Position> positionsChangeDraft = this.positionService.getPositionCanChangedraft();
		result.addObject("positions", positions);
		result.addObject("positionsChangeDraft", positionsChangeDraft);
		result.addObject("owner", true);
		result.addObject("requestURI", "position/company/list.do");
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idPosition) {
		ModelAndView result;

		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal());
		final Position position = this.positionService.findOne(idPosition);

		if (position.getCompany().getId() != company.getId())
			result = new ModelAndView("redirect:list.do");
		else {
			result = new ModelAndView("position/display");
			result.addObject("position", position);
		}

		return result;

	}

}
