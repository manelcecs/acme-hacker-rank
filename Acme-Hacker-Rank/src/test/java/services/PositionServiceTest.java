
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utiles.IntermediaryBetweenTransactions;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;
import domain.Ticker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService					positionService;

	@Autowired
	CompanyService							companyService;

	@Autowired(required = false)
	private IntermediaryBetweenTransactions	intermediaryBetweenTransactions;

	SimpleDateFormat						format	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	/*
	 * This test checks the creation of a new position
	 * 
	 * 1 positive,
	 * 14 negative
	 */

	@Test
	public void newPositionsDriver() throws ParseException {

		final Collection<String> skills = new ArrayList<>();
		skills.add("skill1");
		skills.add("skill2");

		final Collection<String> technologies = new ArrayList<>();
		technologies.add("techno1");
		technologies.add("techno2");

		final Object testingData[][] = {
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) Positivo
				 * c) 63%
				 * d) 17/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"), null
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) La compañía registrada debe ser la que sea la autora de una nueva posición
				 * c) 63%
				 * d) 17/17
				 */

				"company1", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) Un hacker no puede crear una posición
				 * c) 63%
				 * d) 17/17
				 */

				"hacker0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) EL titulo no puede estar en blanco
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) La descripcion no puede estar en blanco
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "titulo valido", "", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) La fecha es obligatoria
				 * c) 63%
				 * d) 15/17
				 */

				"company0", "titulo valido", "descripcion valida", null, "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"), ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) El perfil requerido no puede estar en blanco
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) Las habilidades no pueden ser nulas
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", null, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) Las tecnologias no pueden ser nulas
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, null, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) El salario no puede ser nullo
				 * c) 63%
				 * d) 16/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, null, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) El salario no puede ser menor que 0
				 * c) 63%
				 * d) 17/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, -2.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) La fecha no puede ser en pasado
				 * c) 63%
				 * d)
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2013/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) No se puede crear una posicion que este cancelada
				 * c) 63%
				 * d)
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, true, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			},
			{
				/*
				 * a) Crear una posicion estando registrado
				 * b) No puede meterse html malicioso
				 * c) 63%
				 * d) 17/17
				 */

				"company0", "<script></script>", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, true, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			}, {
				/*
				 * a) Crear una posicion estando registrado
				 * b) El ticker no puede ser null
				 * c) 63%
				 * d) 17/17
				 */

				"company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, true, "company0", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.newPositionsTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (Collection<String>) testingData[i][5], (Collection<String>) testingData[i][6],
				(Double) testingData[i][7], (Boolean) testingData[i][8], (Boolean) testingData[i][9], (String) testingData[i][10], (Ticker) testingData[i][11], (Class<?>) testingData[i][12]);
	}
	protected void newPositionsTemplate(final String user, final String title, final String description, final Date deadline, final String profile, final Collection<String> skils, final Collection<String> technologies, final Double salary,
		final Boolean draft, final Boolean cancelled, final String company, final Ticker ticker, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final Position position = new Position();

			position.setTitle(title);
			position.setDescription(description);
			position.setDeadline(deadline);
			position.setProfileRequired(profile);
			position.setSkillsRequired(skils);
			position.setTechnologiesRequired(technologies);
			position.setSalaryOffered(salary);
			position.setDraft(draft);
			position.setCancelled(cancelled);
			final Company companyOwner = this.companyService.findOne(this.getEntityId(company));
			position.setCompany(companyOwner);
			position.setTicker(ticker);

			this.positionService.save(position);

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test checks the change of draft mode
	 * 
	 * 2 positive,
	 * 2 negative
	 */

	@Test
	public void changeDraftTest() {

		final Position positionValida = this.positionService.findOne(this.getEntityId("position21")); //company 2
		final Position positionLess2Problems = this.positionService.findOne(this.getEntityId("position01")); //Company 0
		final Position positionFinalMode = this.positionService.findOne(this.getEntityId("position00")); //Company 0

		final Object testingData[][] = {
			{
				/*
				 * a) Cambiar el modo borrador estando registrado
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				positionValida, "company2", null
			}, {
				/*
				 * a) Cambiar el modo borrador estando registrado
				 * b) Una compañia no puede cambiar el modo borrador de una position que no es la suya
				 * c) 100%
				 * d)
				 */
				positionValida, "company0", IllegalArgumentException.class
			}, {
				/*
				 * a) Cambiar el modo borrador estando registrado
				 * b) Solo se puede cambiar el modo borrador si tiene dos problemas
				 * c) 100%
				 * d)
				 */
				positionLess2Problems, "company0", IllegalArgumentException.class
			}, {
				/*
				 * a) Cambiar el modo borrador estando registrado
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				positionFinalMode, "company0", null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.changeDraftTemplate((Position) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void changeDraftTemplate(final Position position, final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.positionService.changeDraft(position);

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * This test checks the change of cancel attribute
	 * 
	 * 2 positive,
	 * 2 negative
	 */

	@Test
	public void cancelTest() {

		final Position positionValidaCancel = this.positionService.findOne(this.getEntityId("position00")); //Company 0
		final Position positionValidaUncancel = this.positionService.findOne(this.getEntityId("position22")); //company 2
		final Position positionDraft = this.positionService.findOne(this.getEntityId("position21")); //company 2

		final Object testingData[][] = {
			{
				/*
				 * a) Cambiar el atributo cancelado cuando esta registrado
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				positionValidaCancel, "company0", null
			}, {
				/*
				 * a) Cambiar el atributo cancelado cuando esta registrado
				 * b) Una compañia solo puede cambiar el atributo cancel de sus posiciones
				 * c) 100%
				 * d)
				 */
				positionValidaCancel, "company1", IllegalArgumentException.class
			}, {
				/*
				 * a) Cambiar el atributo cancelado cuando esta registrado
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				positionValidaUncancel, "company3", null
			}, {
				/*
				 * a) Cambiar el atributo cancelado cuando esta registrado
				 * b) No se puede cancelar una posición que esté en modo draft
				 * c) 100%
				 * d)
				 */
				positionDraft, "company2", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelTemplate((Position) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void cancelTemplate(final Position position, final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.positionService.changeCancellation(position);

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * This test checks the delete of a position
	 * 
	 * 1 positive,
	 * 3 negative
	 */

	@Test
	public void deleteTest() {

		final Position positionValida = this.positionService.findOne(this.getEntityId("position01")); //Company 0
		final Position positionFinalMode = this.positionService.findOne(this.getEntityId("position00")); //company 0
		final Position positionCancel = this.positionService.findOne(this.getEntityId("position22")); //company 2

		final Object testingData[][] = {
			{
				/*
				 * a) Borrar la posicion si esta logueado
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				positionValida, "company0", null
			}, {
				/*
				 * a) Borrar la posicion si esta logueado
				 * b) Una compañia solo puede borrar sus posiciones
				 * c) 100%
				 * d)
				 */
				positionValida, "company1", IllegalArgumentException.class
			}, {
				/*
				 * a) Borrar la posicion si esta logueado
				 * b) Solo se puede borrar si esta en modo draft
				 * c) 100%
				 * d)
				 */
				positionFinalMode, "company0", IllegalArgumentException.class
			}, {
				/*
				 * a) Borrar la posicion si esta logueado
				 * b) Una compañia solo puede borrar sus posiciones
				 * c) 100%
				 * d) No se puede borrar una posicion que este cancelada
				 */
				positionCancel, "company2", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((Position) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteTemplate(final Position position, final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.positionService.delete(position);

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * This test checks the update of a position
	 * 
	 * 2 positive,
	 * 12 negative
	 */

	@Test
	public void editTest() throws ParseException {

		final Position positionValida = this.positionService.findOne(this.getEntityId("position01")); //Company 0
		final Position positionFinalMode = this.positionService.findOne(this.getEntityId("position00")); //company 0
		final Position positionCancel = this.positionService.findOne(this.getEntityId("position22")); //company 2

		final Collection<String> skills = new ArrayList<>();
		skills.add("skill1edited");
		skills.add("skill2edited");

		final Collection<String> technologies = new ArrayList<>();
		technologies.add("techno1edited");
		technologies.add("techno2edited");

		final Object testingData[][] = {
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) Positivo
				 * c) 100%
				 * d) 17/17
				 */
				positionValida, "company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				null
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) Una posicion no puede ser editada por una compañia que no es la autora
				 * c) 100%
				 * d) 17/17
				 */
				positionValida, "company1", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) Positivo
				 * c) 100%
				 * d) 17/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, technologies, 1000.0, true, false, "company0",
				this.intermediaryBetweenTransactions.generateTicker("MSI"), null
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) El titulo no puede estar en blanco
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, technologies, 1000.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) La descripcion no puede estar en blanco
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, technologies, 1000.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) La fecha no puede ser nula
				 * c) 100%
				 * d) 15/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", null, "Perfil cambiado", skills, technologies, 1000.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) El perfil no puede estar en blanco
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "", skills, technologies, 1000.0, true, false, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) La lista de skills no puede ser nula
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", null, technologies, 1000.0, true, false, "company0",
				this.intermediaryBetweenTransactions.generateTicker("MSI"), ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) La lista de tecnologias no puede ser nula
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, null, 1000.0, true, false, "company0",
				this.intermediaryBetweenTransactions.generateTicker("MSI"), ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) El salario no puede ser nulo
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, technologies, null, true, false, "company0",
				this.intermediaryBetweenTransactions.generateTicker("MSI"), ConstraintViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) El ticker no puede ser nulo
				 * c) 100%
				 * d) 16/17
				 */
				positionValida, "company0", "cambio de titulo", "cambio de descripcion", this.format.parse("2022/01/01 11:00:00"), "Perfil cambiado", skills, technologies, 1000.0, true, false, "company0", null, DataIntegrityViolationException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) No se puede editar una position que este en modo final
				 * c) 100%
				 * d) 17/17
				 */
				positionFinalMode, "company0", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, false, false, "company0",
				this.intermediaryBetweenTransactions.generateTicker("MSI"), IllegalArgumentException.class
			},
			{
				/*
				 * a) Una position es editada por un usuario logueado
				 * b) No se puede editar una posicion que este en cancelada
				 * c) 100%
				 * d) 17/17
				 */
				positionCancel, "company2", "titulo valido", "descripcion valida", this.format.parse("2020/01/01 11:00:00"), "Perfil valido", skills, technologies, 500.0, false, true, "company0", this.intermediaryBetweenTransactions.generateTicker("MSI"),
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((Position) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], (Collection<String>) testingData[i][6],
				(Collection<String>) testingData[i][7], (Double) testingData[i][8], (Boolean) testingData[i][9], (Boolean) testingData[i][10], (String) testingData[i][11], (Ticker) testingData[i][12], (Class<?>) testingData[i][13]);
	}
	protected void editTemplate(final Position position, final String user, final String title, final String description, final Date deadline, final String profile, final Collection<String> skils, final Collection<String> technologies,
		final Double salary, final Boolean draft, final Boolean cancelled, final String company, final Ticker ticker, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			position.setTitle(title);
			position.setDescription(description);
			position.setDeadline(deadline);
			position.setProfileRequired(profile);
			position.setSkillsRequired(skils);
			position.setTechnologiesRequired(technologies);
			position.setSalaryOffered(salary);
			position.setDraft(draft);
			position.setCancelled(cancelled);
			final Company companyOwner = this.companyService.findOne(this.getEntityId(company));
			position.setCompany(companyOwner);
			position.setTicker(ticker);

			this.positionService.save(position);

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
