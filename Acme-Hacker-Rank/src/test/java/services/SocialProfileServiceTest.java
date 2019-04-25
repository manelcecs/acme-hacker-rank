
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	@Override
	@Before
	public void setUp() {

		this.unauthenticate();

	}

	/**
	 * Test correspondiente a la creación, edición y borrado de un perfil social
	 * Corresponde al requisito funcional 23.1
	 * 
	 * 2 positivos
	 * 5 negativos
	 */
	@Test
	public void SocialProfileCRUDDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a) usuario loggeado como , datos validos
				 * b)Positivo
				 * c)
				 * d)1/1
				 */
				"hacker0", true, null
			}, {
				/*
				 * a) usuario loggeado como , datos invalidos
				 * b) Negativo
				 * c)
				 * d)1/1
				 */
				"hacker0", false, ConstraintViolationException.class
			//			}, {
			//				/*
			//				 * a) usuario loggeado como , datos validos
			//				 * b)Negativo
			//				 * c)
			//				 * d)1/1
			//				 */
			//				"company1", false, null
			//			}, {
			//				/*
			//				 * a) usuario loggeado como , datos invalidos
			//				 * b)Negativo
			//				 * c)
			//				 * d)1/1
			//				 */
			//				"company1", false, ConstraintViolationException.class
			//			}, {
			//				/*
			//				 * a) usuario loggeado como , datos validos
			//				 * b)Positivo
			//				 * c)
			//				 * d)1/1
			//				 */
			//				"admin", true, null
			//			}, {
			//				/*
			//				 * a) usuario loggeado como , datos invalidos
			//				 * b)Negativo
			//				 * c)
			//				 * d)1/1
			//				 */
			//				"admin", false, ConstraintViolationException.class
			//			}, {
			//				/*
			//				 * a) usuario no loggeado, datos validos
			//				 * b)Negativo
			//				 * c)
			//				 * d)1/1
			//				 */
			//				null, true, IllegalArgumentException.class
			//			}
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.socialProfileCRTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
			this.socialProfileUTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
			this.socialProfileDTemplate((String) testingData[i][0], (Class<?>) testingData[i][2]);
		}

	}
	protected void socialProfileCRTemplate(final String user, final boolean validData, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllSocialProfiles(a.getId());

			if (socialProfiles.size() < 1) {
				SocialProfile sp = this.socialProfileService.create();
				sp = this.fillData(sp, a);
				if (!validData)
					sp.setLink("notAValidURL");
				this.socialProfileService.save(sp);
				this.socialProfileService.flush();
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void socialProfileUTemplate(final String user, final boolean validData, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllSocialProfiles(a.getId());
			if (socialProfiles.size() > 0) {
				final SocialProfile psEdit = socialProfiles.iterator().next();
				if (!validData)
					psEdit.setLink("validURL.com/user/dummyUser");
				else
					psEdit.setLink("https://validURL.com/user/dummyUser");

				this.socialProfileService.save(psEdit);
				this.socialProfileService.flush();
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void socialProfileDTemplate(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllSocialProfiles(a.getId());
			if (socialProfiles.size() > 0) {
				final SocialProfile psDelete = socialProfiles.iterator().next();
				this.socialProfileService.delete(psDelete);
				this.socialProfileService.flush();
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private SocialProfile fillData(final SocialProfile sp, final Actor actor) {
		sp.setActor(actor);
		sp.setLink("https://validURL.com");
		sp.setNick("DummyTest" + actor.getName());
		sp.setNameSocialNetwork("DummySN");
		return sp;
	}

	@Override
	@After
	public void tearDown() {
		this.unauthenticate();

	}
}
