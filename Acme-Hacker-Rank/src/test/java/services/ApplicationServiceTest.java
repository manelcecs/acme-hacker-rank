
package services;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Curricula;
import domain.Position;
import forms.ApplicationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	PositionService		positionService;

	@Autowired
	CurriculaService	curriculaService;


	/*
	 * This test checks the creation of an application
	 * 
	 * 1 positive,
	 * 3 negative
	 */
	@Test
	public void newApplicationDriver() throws ParseException {

		final Object testingData[][] = {
			{
				/*
				 * a) Una aplicación es creada por un usuario que esta logueado
				 * b) Positivo
				 * c) 100%
				 * d) 9/12
				 */
				"hacker0", "position00", "curricula00", null
			}, {
				/*
				 * a) Una aplicación es creada por un usuario que esta logueado
				 * b) Una compañia no puede crear aplicaciones
				 * c) 100%
				 * d) 9/12
				 */
				"company1", "position00", "curricula00", IllegalArgumentException.class
			}, {
				/*
				 * a) Una aplicación es creada por un usuario que esta logueado
				 * b) No se puede hacer dos aplicaciones a la misma posicion
				 * c) 100%
				 * d) 9/12
				 */
				"hacker0", "position31", "curricula00", IllegalArgumentException.class
			}, {
				/*
				 * a) Una aplicación es creada por un usuario que esta logueado
				 * b) La curricual tiene que ser del hacker que crea la aplicacion
				 * c) 100%
				 * d) 9/12
				 */
				"hacker0", "position00", "curricula01", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.newAplicationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void newAplicationTemplate(final String user, final String position, final String curricula, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Position positionDB = this.positionService.findOne(this.getEntityId(position));
			final Curricula curriculaDB = this.curriculaService.findOne(this.getEntityId(curricula));

			final ApplicationForm applicationForm = new ApplicationForm();
			applicationForm.setPosition(positionDB);
			applicationForm.setCurricula(curriculaDB);

			this.applicationService.newApplication(applicationForm);
			this.applicationService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test checks the changement of the status of an application
	 * 
	 * 1 positive,
	 * 4 negative
	 */
	@Test
	public void changeStatusDriver() throws ParseException {

		final Object testingData[][] = {
			{
				/*
				 * a) El estado se puede cambiar si el usuario esta logueado
				 * b) El estado tiene que ser ACEPTEDE O REJECTED
				 * c) 100%
				 * d) 3/12
				 */
				"company5", "application5", "ESTADO INVALIDO", IllegalArgumentException.class
			}, {
				/*
				 * a) El estado se puede cambiar si el usuario esta logueado
				 * b) Solo pueden cambiar el estado la compañia propietaria de la posicion a la que hace referencia la application
				 * c) 100%
				 * d) 3/12
				 */
				"company0", "application5", "REJECTED", IllegalArgumentException.class
			}, {
				/*
				 * a) El estado se puede cambiar si el usuario esta logueado
				 * b) No se puede cambiar el estado a pending
				 * c) 100%
				 * d) 3/12
				 */
				"company5", "application5", "PENDING", IllegalArgumentException.class
			}, {
				/*
				 * a) El estado se puede cambiar si el usuario esta logueado
				 * b) No se puede cambiar el estado de una application cuyo estado anterior no sea submitted
				 * c) 100%
				 * d) 3/12
				 */
				"company5", "application1", "ACCEPTED", IllegalArgumentException.class
			}, {
				/*
				 * a) El estado se puede cambiar si el usuario esta logueado
				 * b) Positivo
				 * c) 100%
				 * d) 3/12
				 */
				"company5", "application5", "ACCEPTED", null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.changeStatusTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void changeStatusTemplate(final String user, final String application, final String status, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.applicationService.changeStauts(this.getEntityId(application), status);
			this.applicationService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
