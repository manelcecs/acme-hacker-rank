
package services;

import java.text.ParseException;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Answer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	@Autowired
	AnswerService	answerService;


	/*
	 * This test checks the creation of an answer
	 * 
	 * 1 positive,
	 * 3 negative
	 */

	@Test
	public void createAnswerDriver() throws ParseException {

		final Object testingData[][] = {
			{
				/*
				 * a) Una respuesta es creada por un usuario logueado
				 * b) Positivo
				 * c) 100%
				 * d) 6/6
				 */
				"hacker0", "explicacion valido", "http://www.url.com", "application0", null
			}, {
				/*
				 * a) Una respuesta es creada por un usuario logueado
				 * b) Un hacker no puede crear una respuesta a una aplicacion que no es la suya
				 * c) 100%
				 * d) 6/6
				 */

				"hacker1", "explicacion valido", "http://www.url.com", "application0", IllegalArgumentException.class
			}, {
				/*
				 * a) Una respuesta es creada por un usuario logueado
				 * b) Una compañia no puede crear respuestas
				 * c) 100%
				 * d) 6/6
				 */
				"company0", "explicacion valido", "http://www.url.com", "application0", IllegalArgumentException.class
			}, {
				/*
				 * a) Una respuesta es creada por un usuario logueado
				 * b) La url tiene que ser valida
				 * c) 100%
				 * d) 6/6
				 */
				"hacker1", "explicacion valido", "url invalida", "application1", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAnswerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void createAnswerTemplate(final String user, final String explanation, final String link, final String application, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final Answer answer = this.answerService.create(this.getEntityId(application));
			answer.setExplanation(explanation);
			answer.setLink(link);

			this.answerService.save(answer);

			this.answerService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
