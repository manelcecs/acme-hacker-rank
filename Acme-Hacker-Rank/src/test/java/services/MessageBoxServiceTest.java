
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
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

	@Autowired
	private ActorService		actorService;


	//
	//	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	@Test
	public void NewBoxesDriver() {
		final Object testingData[][] = {
			{
				"company", "Nombre de caja válido", null
			}, {
				"company", "", ConstraintViolationException.class
			}, {
				"company", "<script></script>", ConstraintViolationException.class
			}, {
				"company", "In Box", ConstraintViolationException.class
			}, {
				"company", "Out Box", ConstraintViolationException.class
			}, {
				"company", "Trash Box", ConstraintViolationException.class
			}, {
				"company", "Notification Box", ConstraintViolationException.class
			}, {
				"company", "Spam Box", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.NewBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void OriginalBoxesDriver() {
		final Object testingData[][] = {
			{
				"company", "In Box", "edit", IllegalArgumentException.class
			}, {
				"company", "In Box", "delete", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.OriginalBoxesTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Pruebas con las cajas creadas
	protected void NewBoxesTemplate(final String beanName, final String nameBox, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final MessageBox messageBox = this.messageBoxService.create();
			messageBox.setName(nameBox);
			this.messageBoxService.save(messageBox);
			this.messageBoxService.flush();
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//Pruebas con las cajas creadas
	protected void OriginalBoxesTemplate(final String beanName, final String nameBox, final String accion, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final MessageBox messageBox = this.messageBoxService.findOriginalBox(this.actorService.findByUserAccount(LoginService.getPrincipal()).getId(), nameBox);

			if (accion.equals("delete"))
				this.messageBoxService.delete(messageBox);

			if (accion.equals("edit")) {
				messageBox.setName("Nuevo nombre");
				this.messageBoxService.save(messageBox);
			}

			this.messageBoxService.flush();
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	//utiles
	//	private Date DateNow() throws ParseException {
	//		final LocalDateTime DateTimeNow = LocalDateTime.now();
	//		final Date moment = this.FORMAT.parse(DateTimeNow.getYear() + "/" + DateTimeNow.getMonthOfYear() + "/" + DateTimeNow.getDayOfMonth() + " " + DateTimeNow.getHourOfDay() + ":" + DateTimeNow.getMinuteOfHour() + ":" + DateTimeNow.getSecondOfMinute());
	//		return moment;
	//	}
}
