
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Override
	@Before
	public void setUp() {

		this.unauthenticate();

	}

	/**
	 * Este test hace referencia al registro de un nuevo administrador.
	 * Corresponde con el requisito funcional 11.1.
	 * Además de datos válidos, es requisito indispensable que para registrar
	 * un administrador nuevo, lo haga un adminsitrador ya existente.
	 * 1 test positivo
	 * 4 test negativos
	 */
	@Test
	public void AdminRegisterDriver() {
		final Object testingData[][] = {
			{
				/*
				 * usuario no logeado
				 * a)11.1.
				 * b)Negativo
				 * c)13%
				 * d)2/4
				 */
				null, true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como administratdor
				 * a)11.1
				 * b)Positivo
				 * c)100%
				 * d)2/4
				 */
				"admin", true, null
			}, {
				/*
				 * usuario logeado como hacker
				 * a)11.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"hacker", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como company
				 * a)11.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"company", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como administrador, datos invalidos.(email y photoURL)
				 * a)11.1
				 * b)Negativo
				 * c)100%
				 * d)2/4
				 */
				"admin", false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AdminRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void AdminRegisterTemplate(final String username, final boolean validData, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		Administrator admin = this.administratorService.create();
		try {
			this.authenticate(username);

			if (!validData) {
				admin.setEmail("");
				admin.setPhoto("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				admin.setEmail("<admin" + uniqueId + "@>");
				admin.setPhoto("https://tiny.url/dummyPhoto");
			}
			admin = AdministratorServiceTest.fillData(admin);
			this.administratorService.save(admin);
			this.administratorService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
		System.out.println("Test concluido con éxito: " + username);
	}
	@Override
	@After
	public void tearDown() {

		this.unauthenticate();

	}

	private static Administrator fillData(final Administrator admin) {
		final Administrator res = admin;

		res.setAddress("Dirección de prueba");
		res.setBanned(false);

		res.setName("DummyBoss");
		res.setPhoneNumber("+34 555-2342");
		res.setSpammer(false);

		final String dummySurname = "Dummy Wane Dan";
		final String surnames[] = dummySurname.split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		res.setSurnames(surNames);

		final UserAccount a = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		a.addAuthority(auth);
		a.setUsername("DummyTest" + res.getEmail());
		a.setPassword("DummyPass");

		res.setUserAccount(a);

		final CreditCard cc = new CreditCard();
		cc.setCvv(231);
		cc.setExpirationMonth(04);
		cc.setExpirationYear(22);
		cc.setHolder(res.getName());
		cc.setMake("VISA");
		cc.setNumber("4308543581357191");

		res.setCreditCard(cc);

		res.setVatNumber("888848545-R");

		return res;
	}

}
