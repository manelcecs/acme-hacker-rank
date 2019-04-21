
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private ActorService			actorService;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


	@Test
	public void sendMessageDriver() throws ParseException {
		final Object testingData[][] = {
			{
				"hacker0", "hacker0", "hacker1", "body", "subject", "tag", "HIGH", null
			}, {
				"hacker0", "hacker0", "hacker1", "", "subject", "tag", "HIGH", ConstraintViolationException.class
			}, {
				"hacker0", "hacker0", "hacker1", "body", "", "tag", "HIGH", ConstraintViolationException.class
			}, {
				"hacker0", "hacker0", "hacker1", "body", "subject", "tag", "HIGH", ConstraintViolationException.class
			}, {
				"hacker0", "hacker0", "hacker1", "body", "subject", "tag", "", ConstraintViolationException.class
			}, {
				"hacker0", "hacker0", "hacker1", "body", "subject", "tag", "No priority", ConstraintViolationException.class
			}, {
				"hacker0", "hacker0", "hacker1", "body", "subject", "tag", "", ConstraintViolationException.class
			}, {
				"hacker0", "company0", "hacker1", "body", "subject", "tag", "No priority", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sendMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void sendMessageTemplate(final String userLogged, final String sender, final String recipient, final String body, final String subject, final String tag, final String priority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(userLogged);

			final int idSender = this.getEntityId(sender);
			final int idRecipient = this.getEntityId(recipient);

			final Message message = this.messageService.create();
			message.setSender(this.actorService.getActor(idSender));
			message.setBody(body);
			message.setSubject(subject);

			final Collection<String> tags = new ArrayList<>();
			tags.add(tag);
			message.setTags(tags);

			message.setMoment(this.dateNow());

			message.setPriority(priority);

			final Collection<Actor> recipients = new ArrayList<>();
			recipients.add(this.actorService.getActor(idRecipient));
			message.setRecipients(recipients);

			this.messageService.save(message);
			this.messageService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteMessageDriver() throws ParseException {
		final Object testingData[][] = {
			{
				"hacker0", "hacker0", "hacker1", null
			}, {
				"hacker1", "hacker0", "hacker1", null
			}, {
				"company0", "hacker0", "hacker1", IllegalArgumentException.class
			}, {
				"admin", "hacker0", "hacker1", IllegalArgumentException.class
			}, {
				"hacker2", "hacker0", "hacker1", IllegalArgumentException.class
			}, {
				null, "hacker0", "hacker1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void deleteMessageTemplate(final String userLogged, final String sender, final String recipient, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(sender);

			final int idSender = this.getEntityId(sender);
			final int idRecipient = this.getEntityId(recipient);

			final Message message = this.messageService.create();
			message.setSender(this.actorService.getActor(idSender));
			message.setBody("Body");
			message.setSubject("Subject");
			message.setMoment(this.dateNow());
			message.setTags(null);
			message.setPriority("HIGH");
			final Collection<Actor> recipients = new ArrayList<>();
			recipients.add(this.actorService.getActor(idRecipient));
			message.setRecipients(recipients);

			final Message messageBD = this.messageService.save(message);
			this.messageService.flush();
			super.unauthenticate();

			super.authenticate(userLogged);

			this.messageService.delete(messageBD);
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	public void testPrueba() throws ParseException {
		super.authenticate("hacker0");

		final int idUser = this.getEntityId("hacker0");
		final int idOwner = this.getEntityId("hacker1");

		final Message message = this.messageService.create();
		message.setSender(this.actorService.getActor(idUser));
		message.setBody("Body");
		message.setMoment(this.dateNow());
		message.setPriority("HIGH");
		final Collection<Actor> recipients = new ArrayList<>();
		recipients.add(this.actorService.getActor(idOwner));
		message.setTags(null);
		message.setRecipients(recipients);
		message.setSubject("Subject");
		message.setBody("Editando un mensaje");

		final Message messageSaved = this.messageService.save(message);
		this.messageService.flush();

		messageSaved.setBody("Editando");

		this.messageService.save(messageSaved);
		this.messageService.flush();

		super.unauthenticate();
	}

	//utiles
	private Date dateNow() throws ParseException {
		final LocalDateTime DateTimeNow = LocalDateTime.now();
		final Date moment = this.FORMAT.parse(DateTimeNow.getYear() + "/" + DateTimeNow.getMonthOfYear() + "/" + DateTimeNow.getDayOfMonth() + " " + DateTimeNow.getHourOfDay() + ":" + DateTimeNow.getMinuteOfHour() + ":" + DateTimeNow.getSecondOfMinute());
		return moment;
	}

	@Test
	public void spamMessage() throws ParseException {
		super.authenticate("hacker0");

		final int idSender = this.getEntityId("hacker0");
		final int idRecipient = this.getEntityId("hacker1");

		final Message message = this.messageService.create();
		message.setSender(this.actorService.getActor(idSender));
		message.setBody("sexo"); //Spam Word
		message.setMoment(this.dateNow());
		message.setPriority("HIGH");
		final Collection<Actor> recipients = new ArrayList<>();
		recipients.add(this.actorService.getActor(idRecipient));
		message.setTags(null);
		message.setRecipients(recipients);
		message.setSubject("Subject");

		final Message messageSaved = this.messageService.save(message);
		this.messageService.flush();

		final Collection<MessageBox> boxes = messageSaved.getMessageBoxes();

		final MessageBox spamBoxRecipient = this.messageBoxService.findOriginalBox(idRecipient, "Spam Box");

		Assert.isTrue(boxes.contains(spamBoxRecipient));

		this.messageService.flush();
	}

	@Test
	public void broadCastMessage() throws ParseException {
		super.authenticate("admin");

		final int idSender = this.getEntityId("admin");

		final Message message = this.messageService.create();
		message.setSender(this.actorService.getActor(idSender));
		message.setBody("notification");
		message.setMoment(this.dateNow());
		message.setPriority("HIGH");
		final List<Actor> recipients = (List<Actor>) this.actorService.findNonEliminatedActors();
		recipients.remove(this.actorService.getActor(idSender));
		message.setRecipients(recipients);

		message.setTags(null);
		message.setSubject("Subject");

		final Message messageSaved = this.messageService.save(message);
		this.messageService.flush();

		final Collection<MessageBox> boxes = messageSaved.getMessageBoxes();

		final MessageBox notificationBox = this.messageBoxService.findOriginalBox(recipients.get(0).getId(), "Notification Box");

		Assert.isTrue(boxes.contains(notificationBox));

		this.messageService.flush();
	}
}
