
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import repositories.MessageBoxRepository;
import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private Validator				validator;


	public MessageBox create() {
		final MessageBox box = new MessageBox();
		box.setDeleteable(true);

		return box;
	}

	public MessageBox save(final MessageBox messageBox) {
		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());

		if (messageBox.getId() != 0) {
			final MessageBox boxBD = this.messageBoxRepository.findOne(messageBox.getId());
			Assert.isTrue(boxBD.getDeleteable() == messageBox.getDeleteable());
			Assert.isTrue(messageBox.getDeleteable());
		}

		final String nameBox = messageBox.getName().toUpperCase().trim();
		Assert.isTrue(!(nameBox.equals("IN BOX") || nameBox.equals("TRASH BOX") || nameBox.equals("OUT BOX") || nameBox.equals("SPAM BOX") || nameBox.equals("NOTIFICATION BOX")));

		final MessageBox boxSave = this.messageBoxRepository.save(messageBox);
		if (messageBox.getId() == 0) {
			final Collection<MessageBox> messageBoxes = actor.getMessageBoxes();
			messageBoxes.add(boxSave);
			actor.setMessageBoxes(messageBoxes);
			this.actorService.save(actor);
		}

		return boxSave;
	}

	public void delete(final MessageBox messageBox) {
		Assert.isTrue(messageBox.getDeleteable());
		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(this.actorService.getByMessageBox(messageBox.getId()).equals(actor));

		final Collection<MessageBox> childrens = this.allChildren(messageBox);

		final Collection<Message> messagesAlaTrashBox = new ArrayList<>();
		final MessageBox trashBox = this.findOriginalBox(actor.getId(), "Trash Box");
		for (final MessageBox childrensAndFather : childrens)
			if (childrensAndFather.getMessages().size() > 0) {
				messagesAlaTrashBox.addAll(childrensAndFather.getMessages());
				for (final Message messageInBox : childrensAndFather.getMessages()) {
					final Collection<MessageBox> boxes = messageInBox.getMessageBoxes();
					boxes.remove(childrensAndFather);
					if (boxes.size() == 0)
						boxes.add(trashBox);
					messageInBox.setMessageBoxes(boxes);
					this.messageRepository.save(messageInBox);
				}
				childrensAndFather.setMessages(null);
				this.messageBoxRepository.save(childrensAndFather);
			}
		final Collection<Message> trashBoxMessages = trashBox.getMessages();
		trashBoxMessages.addAll(messagesAlaTrashBox);
		trashBox.setMessages(trashBoxMessages);
		this.messageBoxRepository.save(trashBox);

		final Collection<MessageBox> boxesActor = actor.getMessageBoxes();
		boxesActor.removeAll(childrens);
		actor.setMessageBoxes(boxesActor);
		this.actorRepository.save(actor);

		this.messageBoxRepository.delete(childrens);
	}

	public MessageBox findOne(final int id) {
		return this.messageBoxRepository.findOne(id);
	}

	public MessageBox findOriginalBox(final int id, final String string) {
		return this.messageBoxRepository.findOriginalBox(id, string);
	}

	public Collection<MessageBox> findBoxToMove(final Message message) {
		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<MessageBox> messageBoxesToMove = this.findAllMessageBoxByActor(actor.getId());

		final MessageBox trashBox = this.messageBoxRepository.findOriginalBox(actor.getId(), "Trash Box");
		final MessageBox inBox = this.messageBoxRepository.findOriginalBox(actor.getId(), "In Box");
		final MessageBox outBox = this.messageBoxRepository.findOriginalBox(actor.getId(), "Out Box");
		final MessageBox notificationBox = this.messageBoxRepository.findOriginalBox(actor.getId(), "Notification Box");

		messageBoxesToMove.removeAll(message.getMessageBoxes());
		messageBoxesToMove.remove(trashBox);
		messageBoxesToMove.remove(inBox);
		messageBoxesToMove.remove(outBox);
		messageBoxesToMove.remove(notificationBox);

		return messageBoxesToMove;
	}

	private Collection<MessageBox> findAllMessageBoxByActor(final int id) {
		return this.messageBoxRepository.findAllMessageBoxByActor(id);
	}

	public Collection<MessageBox> findPosibleParents(final int id) {
		return this.messageBoxRepository.findPosibleParents(id);
	}

	public Collection<MessageBox> findChildren(final int id) {
		return this.messageBoxRepository.findChildren(id);
	}

	public Collection<MessageBox> findAllMessageBoxByActorContainsAMessage(final int idActor, final int idMessage) {
		return this.messageBoxRepository.findAllMessageBoxByActorContainsAMessage(idActor, idMessage);
	}

	public Collection<MessageBox> initializeNewUserBoxes() {
		final List<MessageBox> res = new ArrayList<>();

		final MessageBox in = this.create();
		in.setDeleteable(false);
		in.setName("In Box");
		final MessageBox inF = this.messageBoxRepository.save(in);

		final MessageBox out = this.create();
		out.setDeleteable(false);
		out.setName("Out Box");
		final MessageBox outF = this.messageBoxRepository.save(out);

		final MessageBox spam = this.create();
		spam.setDeleteable(false);
		spam.setName("Spam Box");
		final MessageBox spamF = this.messageBoxRepository.save(spam);
		final MessageBox trash = this.create();

		trash.setDeleteable(false);
		trash.setName("Trash Box");
		final MessageBox trashF = this.messageBoxRepository.save(trash);

		final MessageBox notification = this.create();
		notification.setName("Notification Box");
		notification.setDeleteable(false);
		final MessageBox notificationF = this.messageBoxRepository.save(notification);

		res.add(inF);
		res.add(outF);
		res.add(trashF);
		res.add(spamF);
		res.add(notificationF);

		return res;
	}

	public MessageBox reconstruct(final MessageBox messageBox, final BindingResult binding) {
		MessageBox result;
		if (messageBox.getId() == 0) {
			result = this.create();
			result.setMessages(null);
			result.setDeleteable(true);
			result.setParent(messageBox.getParent());
			result.setName(messageBox.getName());
		} else {
			result = this.findOne(messageBox.getId());
			result.setParent(messageBox.getParent());
			result.setName(messageBox.getName());
		}

		this.validator.validate(result, binding);

		final String nameBox = messageBox.getName().toUpperCase().trim();
		if (((nameBox.equals("IN BOX") || nameBox.equals("TRASH BOX") || nameBox.equals("OUT BOX") || nameBox.equals("SPAM BOX") || nameBox.equals("NOTIFICATION BOX"))))
			binding.rejectValue("name", "messageBox.error.name");

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	private Collection<MessageBox> allChildren(final MessageBox box) {
		final Collection<MessageBox> acum = new ArrayList<>();
		return this.allChildren(box, acum);
	}

	private Collection<MessageBox> allChildren(final MessageBox box, final Collection<MessageBox> acum) {
		final Collection<MessageBox> childrens = this.findChildren(box.getId());
		if (childrens.size() == 0)
			acum.add(box);
		else {
			for (final MessageBox child : childrens)
				this.allChildren(child, acum);
			acum.add(box);
		}
		return acum;
	}

	public void flush() {
		this.messageBoxRepository.flush();
	}

}
