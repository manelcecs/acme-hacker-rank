
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private EducationDataService		educationDataService;
	@Autowired
	private PositionDataService			positionDataService;


	@Override
	@Before
	public void setUp() {

		this.unauthenticate();

	}

	@Test
	public void createCurriculaAndPersonalDataDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a) usuario loggeado como hacker, datos validos
				 * b)Positivo
				 * c)100%
				 * d)6/6
				 */
				"hacker0", true, null
			}, {
				/*
				 * a) usuario loggeado como hacker, datos invalidos
				 * b) Negativo
				 * c)96%
				 * d)6/6
				 */
				"hacker0", false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCurriculaAndPersonalDataTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void createCurriculaAndPersonalDataTemplate(final String username, final boolean validData, final Class<?> expected) {
		Class<?> caught = null;
		try {

			this.authenticate(username);

			final Curricula c = this.curriculaService.create();
			c.setTitle("DummyCurricula");
			final Curricula res = this.curriculaService.save(c);
			this.curriculaService.flush();

			final PersonalData personalData = this.personalDataService.create(res.getId());
			personalData.setFullName("Dummy Test Personal");
			personalData.setPhoneNumber("6547382683");
			personalData.setStatement("Some dummy text");

			if (validData) {
				personalData.setLinkedinProfile("https://github.com/u/");
				personalData.setGitHubProfile("https://linkedIn.com/user");

			} else {
				personalData.setLinkedinProfile("");
				personalData.setGitHubProfile("");
			}

			final MiscellaneousData miscData = this.miscellaneousDataService.create(res.getId());
			miscData.setText("Some Dummy Test");

			final EducationData eduData = this.educationDataService.create(res.getId());
			eduData.setDegree("Dummy Degree");
			eduData.setInstitution("Dummy University");
			eduData.setMark("Dummy Pass");
			if (validData) {
				eduData.setStartDate(LocalDateTime.parse("2010/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
				eduData.setEndDate(LocalDateTime.parse("2019/01/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
			} else {
				eduData.setStartDate(LocalDateTime.parse("2020/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
				eduData.setEndDate(LocalDateTime.parse("2009/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
			}

			final PositionData posData = this.positionDataService.create(res.getId());
			posData.setDescription("Dummy Description data");
			posData.setTitle("Dummy title");
			if (validData) {
				posData.setStartDate(LocalDateTime.parse("2010/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
				posData.setEndDate(LocalDateTime.parse("2019/01/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
			} else {
				posData.setStartDate(LocalDateTime.parse("2020/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
				posData.setEndDate(LocalDateTime.parse("2009/02/10 10:10:00", new DateTimeFormatterFactory("YYYY/MM/dd hh:mm:ss").createDateTimeFormatter()).toDate());
			}
			this.personalDataService.save(personalData);
			this.personalDataService.flush();
			this.miscellaneousDataService.save(miscData);
			this.miscellaneousDataService.flush();
			this.educationDataService.save(eduData);
			this.educationDataService.flush();
			this.positionDataService.save(posData);
			this.positionDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Override
	@After
	public void tearDown() {

		this.unauthenticate();

	}
}
