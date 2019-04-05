
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private MessageService		messageService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Actor sender = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Message message = this.messageService.create(sender);

		result = this.createEditModelAndView(message);
		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("Message") @Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				this.messageService.save(message);
				result = new ModelAndView("redirect:../messageBox/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idMessage) {
		final ModelAndView result;

		final Message message = this.messageService.findOne(idMessage);
		Assert.notNull(idMessage);
		final Collection<MessageBox> boxesToMove = this.messageBoxService.findBoxToMove(message);

		result = new ModelAndView("message/display");

		result.addObject("Message", message);
		result.addObject("recipients", this.messageService.getRecipients(idMessage));
		result.addObject("tags", message.getTags());
		result.addObject("boxesToMove", boxesToMove);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idMessage) {
		ModelAndView result;
		final Message message = this.messageService.findOne(idMessage);

		try {
			this.messageService.delete(message);
			result = new ModelAndView("redirect:../messageBox/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../messageBox/list.do");
			result.addObject("message", "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/addTo", method = RequestMethod.POST)
	public ModelAndView addTo(@ModelAttribute("Message") Message message, final BindingResult binding) {
		ModelAndView result;
		message = this.messageService.reconstruct(message, binding);
		if (binding.hasErrors())
			result = this.addModelAndView(message);
		else
			try {
				this.messageService.addToBox(message);
				result = new ModelAndView("redirect:../messageBox/list.do");
			} catch (final Throwable oops) {
				result = this.addModelAndView(message, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/removeFrom", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int idMessageBox, final int idMessage) {
		ModelAndView result;
		final Message message = this.messageService.findOne(idMessage);
		Assert.notNull(message);
		final MessageBox messageBox = this.messageBoxService.findOne(idMessageBox);
		Assert.notNull(messageBox);

		try {
			this.messageService.removeFrom(message, messageBox);
			result = new ModelAndView("redirect:../messageBox/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../messageBox/list.do");
			result.addObject("message", "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message) {

		final ModelAndView result;

		result = this.createEditModelAndView(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("message/send");

		final Actor sender = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(sender);

		result.addObject("actors", actors);

		result.addObject("Message", message);

		result.addObject("message", messageCode);

		return result;
	}
	protected ModelAndView addModelAndView(final Message message) {

		final ModelAndView result;

		result = this.addModelAndView(message, null);
		return result;
	}

	protected ModelAndView addModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("message/display");

		final Collection<MessageBox> boxesToMove = this.messageBoxService.findBoxToMove(message);

		result.addObject("Message", message);
		result.addObject("boxesToMove", boxesToMove);

		result.addObject("message", messageCode);
		return result;
	}
}
