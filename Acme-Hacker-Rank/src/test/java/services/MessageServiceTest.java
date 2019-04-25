
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


	@Test
	public void editMessageDriver() throws ParseException {
		final Object testingData[][] = {
			{
				"hacker0", "hacker1", "In Box", IllegalArgumentException.class
			}
		//			}, {
		//				"hacker0", "hacker0", "Out Box", IllegalArgumentException.class
		//			}, {
		//				"hacker0", "hacker0", "Notification Box", IllegalArgumentException.class
		//			}, {
		//				"hacker0", "hacker0", "Spam Box", IllegalArgumentException.class
		//			}, {
		//				"hacker0", "hacker0", "Trash Box", IllegalArgumentException.class
		//			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void editMessageTemplate(final String user, final String owner, final String nameBox, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final int idUser = this.getEntityId(user);
			final int idOwner = this.getEntityId(owner);

			final Message message = this.messageService.create();
			message.setSender(this.actorService.getActor(idUser));
			message.setBody("Body");
			message.setMoment(this.dateNow());
			message.setTags(null);
			message.setPriority("HIGH");
			final Collection<Actor> recipients = new ArrayList<>();
			recipients.add(this.actorService.getActor(idOwner));
			message.setRecipients(recipients);
			message.setSubject("Subject");
			message.setBody("Editando un mensaje");

			final Message messageSaved = this.messageService.save(message);
			this.messageService.flush();

			messageSaved.setBody("Editando");

			this.messageService.save(messageSaved);
			this.messageService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	//	//TODO: si pongo un elemento mas falla
	//	@Test
	//	public void editOriginalBoxesDriver() {
	//		final Object testingData[][] = {
	//			{
	//				"hacker0", "hacker0", "In Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Out Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Trash Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Notification Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Spam Box", IllegalArgumentException.class
	//			}
	//		//,{
	//		//				"hacker0", "admin", "In Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Out Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Trash Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Notification Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Spam Box", IllegalArgumentException.class
	//		//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.editOriginalBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	//	}
	//	protected void editOriginalBoxesTemplate(final String user, final String owner, final String nameBox, final Class<?> expected) {
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			super.authenticate(user);
	//
	//			final int idActor = this.getEntityId(user);
	//
	//			final MessageBox originalBox = this.messageBoxService.findOriginalBox(idActor, nameBox);
	//
	//			originalBox.setName("Hola");
	//
	//			this.messageBoxService.save(originalBox);
	//			this.messageBoxService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
	//
	//	@Test
	//	public void deleteOriginalBoxesDriver() {
	//		final Object testingData[][] = {
	//			{
	//				"hacker0", "hacker0", "In Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Out Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Trash Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Notification Box", IllegalArgumentException.class
	//			}, {
	//				"hacker0", "hacker0", "Spam Box", IllegalArgumentException.class
	//			}
	//		//,{
	//		//				"hacker0", "admin", "In Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Out Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Trash Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Notification Box", IllegalArgumentException.class
	//		//			}, {
	//		//				"hacker0", "admin", "Spam Box", IllegalArgumentException.class
	//		//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.deleteOriginalBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	//	}
	//	protected void deleteOriginalBoxesTemplate(final String user, final String owner, final String nameBox, final Class<?> expected) {
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			super.authenticate(user);
	//
	//			final int idActor = this.getEntityId(user);
	//
	//			final MessageBox originalBox = this.messageBoxService.findOriginalBox(idActor, nameBox);
	//
	//			this.messageBoxService.delete(originalBox);
	//			this.messageBoxService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}

	@Test
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
	//TODO: Probar con LocalDateTime.now().toDate()
	private Date dateNow() throws ParseException {
		final LocalDateTime DateTimeNow = LocalDateTime.now();
		final Date moment = this.FORMAT.parse(DateTimeNow.getYear() + "/" + DateTimeNow.getMonthOfYear() + "/" + DateTimeNow.getDayOfMonth() + " " + DateTimeNow.getHourOfDay() + ":" + DateTimeNow.getMinuteOfHour() + ":" + DateTimeNow.getSecondOfMinute());
		return moment;
	}

}
