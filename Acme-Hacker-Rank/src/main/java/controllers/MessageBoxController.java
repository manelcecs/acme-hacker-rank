
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageBoxService;
import domain.Actor;
import domain.MessageBox;

@Controller
@RequestMapping("/messageBox")
public class MessageBoxController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageBoxService	messageBoxService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<MessageBox> boxes;
		final UserAccount principal = LoginService.getPrincipal();

		final Actor actor = this.actorService.findByUserAccount(principal);

		final MessageBox boxDefault = this.messageBoxService.findOriginalBox(actor.getId(), "In Box");

		boxes = actor.getMessageBoxes();

		result = new ModelAndView("messageBox/list");
		result.addObject("boxes", boxes);
		result.addObject("boxSelect", boxDefault);
		result.addObject("messages", boxDefault.getMessages());
		result.addObject("requestURI", "messageBox/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idBox) {
		final ModelAndView result;
		final Collection<MessageBox> boxes;
		final UserAccount principal = LoginService.getPrincipal();

		final Actor actor = this.actorService.findByUserAccount(principal);
		final MessageBox boxDefault = this.messageBoxService.findOne(idBox);

		boxes = actor.getMessageBoxes();

		result = new ModelAndView("messageBox/list");
		result.addObject("boxes", boxes);
		result.addObject("boxSelect", boxDefault);
		result.addObject("messages", boxDefault.getMessages());
		result.addObject("requestURI", "messageBox/show.do?idBox=" + idBox);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final MessageBox messageBox = this.messageBoxService.create();

		result = this.createEditModelAndView(messageBox);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idMessageBox) {
		final ModelAndView result;

		final MessageBox box = this.messageBoxService.findOne(idMessageBox);
		Assert.notNull(box);
		result = this.createEditModelAndView(box);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;

		try {
			final MessageBox messageBoxRec = this.messageBoxService.reconstruct(messageBox, binding);
			this.messageBoxService.save(messageBoxRec);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(messageBox);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idMessageBox) {
		ModelAndView result;
		final MessageBox box = this.messageBoxService.findOne(idMessageBox);
		try {
			this.messageBoxService.delete(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "messageBox.commit.error");
			oops.printStackTrace();
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox) {

		final ModelAndView result;

		result = this.createEditModelAndView(messageBox, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("messageBox/edit");

		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Collection<MessageBox> posibleParents = this.messageBoxService.findPosibleParents(actor.getId());
		posibleParents.remove(messageBox); //Elimino a la box editable
		final Collection<MessageBox> childrens = this.messageBoxService.findChildren(messageBox.getId());
		posibleParents.remove(childrens); //Y elimino a sus hijos, que no pueden ser sus padres

		result.addObject("messageBox", messageBox);
		result.addObject("posibleParents", posibleParents);

		result.addObject("message", messageCode);
		return result;
	}
}
