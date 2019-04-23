
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.AdminConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminConfigServiceTest extends AbstractTest {

	@Autowired
	private AdminConfigService	adminConfigService;


	@Test
	public void editAdminConfigDriver() {
		final Object testingData[][] = {
			{
				"admin", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", null
			}, {
				"company1", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"hacker1", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"admin", "es no es una url", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", -1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 25, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+34", 0, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+34", 101, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+1000", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+34", 10, "", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "", "Hola, bienvenido", ConstraintViolationException.class
			}, {
				"admin", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editAdminConfigTemplate((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void editAdminConfigTemplate(final String beanName, final String bannerURL, final Integer cacheFinder, final String countryCode, final Integer resultsFinder, final String systemName, final String welcomeMessageEN,
		final String welcomeMessageES, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
			adminConfig.setBannerURL(bannerURL);
			adminConfig.setCacheFinder(cacheFinder);
			adminConfig.setCountryCode(countryCode);
			adminConfig.setResultsFinder(resultsFinder);
			adminConfig.setSystemName(systemName);
			adminConfig.setWelcomeMessageEN(welcomeMessageEN);
			adminConfig.setWelcomeMessageES(welcomeMessageES);
			this.adminConfigService.save(adminConfig);
			this.adminConfigService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void editSpamWordTemplate() {
		final Object testingData[][] = {
			{
				"admin", "nueva palabra", "add", null
			}, {
				"admin", "sex", "delete", null
			}, {
				"admin", "dvdsdvsdv", "delete", IllegalArgumentException.class
			}, {
				"hacker0", "sex", "delete", IllegalArgumentException.class
			}, {
				"hacker0", "nueva palabra", "add", IllegalArgumentException.class
			}, {
				"company0", "sex", "delete", IllegalArgumentException.class
			}, {
				"company0", "palabra", "add", IllegalArgumentException.class
			}, {
				null, "sex", "delete", IllegalArgumentException.class
			}, {
				null, "palabra", "add", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editSpamWordTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void editSpamWordTemplate(final String user, final String spamWord, final String action, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);
			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();

			final Collection<String> spamWords = adminConfig.getSpamWords();

			if (action == "add") {
				spamWords.add(spamWord);
				adminConfig.setSpamWords(spamWords);
				this.adminConfigService.save(adminConfig);
			} else if (action == "delete")
				this.adminConfigService.deleteSpamWord(spamWord);

			this.adminConfigService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
