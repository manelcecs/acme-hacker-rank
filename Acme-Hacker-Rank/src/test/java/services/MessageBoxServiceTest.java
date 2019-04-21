
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageBoxServiceTest extends AbstractTest {

	@Autowired
	private MessageBoxService	messageBoxService;


	@Test
	public void newBoxesDriver() {
		final Object testingData[][] = {
			{
				"hacker0", "nombre valido", null
			}, {
				"hacker0", "In Box", IllegalArgumentException.class
			}, {
				"hacker0", "Out Box", IllegalArgumentException.class
			}, {
				"hacker0", "Notification Box", IllegalArgumentException.class
			}, {
				"hacker0", "Spam Box", IllegalArgumentException.class
			}, {
				"hacker0", "Trash Box", IllegalArgumentException.class
			}, {
				"hacker0", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.newBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void newBoxesTemplate(final String user, final String nameBox, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final MessageBox messageBox = this.messageBoxService.create();

			messageBox.setDeleteable(true);
			messageBox.setName(nameBox);

			this.messageBoxService.save(messageBox);

			this.messageBoxService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void editOriginalBoxesDriver() {
		final Object testingData[][] = {
			{
				"hacker0", "In Box", IllegalArgumentException.class
			}, {
				"hacker0", "Out Box", IllegalArgumentException.class
			}, {
				"hacker0", "Trash Box", IllegalArgumentException.class
			}, {
				"hacker0", "Notification Box", IllegalArgumentException.class
			}, {
				"hacker0", "Spam Box", IllegalArgumentException.class
			}, {
				"company0", "In Box", IllegalArgumentException.class
			}, {
				"company0", "Out Box", IllegalArgumentException.class
			}, {
				"company0", "Trash Box", IllegalArgumentException.class
			}, {
				"company0", "Notification Box", IllegalArgumentException.class
			}, {
				"company0", "Spam Box", IllegalArgumentException.class
			}, {
				"admin", "In Box", IllegalArgumentException.class
			}, {
				"admin", "Out Box", IllegalArgumentException.class
			}, {
				"admin", "Trash Box", IllegalArgumentException.class
			}, {
				"admin", "Notification Box", IllegalArgumentException.class
			}, {
				"admin", "Spam Box", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editOriginalBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void editOriginalBoxesTemplate(final String user, final String nameBox, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final int idActor = this.getEntityId(user);

			final MessageBox originalBox = this.messageBoxService.findOriginalBox(idActor, nameBox);

			originalBox.setName("Hola");

			this.messageBoxService.save(originalBox);
			this.messageBoxService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteOriginalBoxesDriver() {
		final Object testingData[][] = {
			{
				"hacker0", "hacker0", "In Box", IllegalArgumentException.class
			}, {
				"hacker0", "hacker0", "Out Box", IllegalArgumentException.class
			}, {
				"hacker0", "hacker0", "Trash Box", IllegalArgumentException.class
			}, {
				"hacker0", "hacker0", "Notification Box", IllegalArgumentException.class
			}, {
				"hacker0", "hacker0", "Spam Box", IllegalArgumentException.class
			}, {
				"hacker0", "admin", "In Box", IllegalArgumentException.class
			}, {
				"hacker0", "admin", "Out Box", IllegalArgumentException.class
			}, {
				"hacker0", "admin", "Trash Box", IllegalArgumentException.class
			}, {
				"hacker0", "admin", "Notification Box", IllegalArgumentException.class
			}, {
				"hacker0", "admin", "Spam Box", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteOriginalBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void deleteOriginalBoxesTemplate(final String user, final String owner, final String nameBox, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final int idActor = this.getEntityId(user);

			final MessageBox originalBox = this.messageBoxService.findOriginalBox(idActor, nameBox);

			this.messageBoxService.delete(originalBox);
			this.messageBoxService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
