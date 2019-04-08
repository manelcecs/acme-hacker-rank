
package services;

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


	//La ultima siempre lanza una IAE
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
				"admin", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", null
			}, {
				"hacker1", "http://url.com", 1, "+34", 10, "Acme-Hacker-Rank", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
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
	public void testPrueba() {
		super.authenticate("admin");
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		adminConfig.setBannerURL("esto no es una url");
		adminConfig.setCacheFinder(1);
		adminConfig.setCountryCode("+34");
		adminConfig.setResultsFinder(10);
		adminConfig.setSystemName("Acme-Hacker-Rank");
		adminConfig.setWelcomeMessageEN("Hola, bienvenido");
		adminConfig.setWelcomeMessageES("Hola, bienvenido");
		this.adminConfigService.save(adminConfig);
		super.authenticate(null);
		this.adminConfigService.flush();
	}

}
