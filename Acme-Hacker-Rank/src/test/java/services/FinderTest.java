
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderTest extends AbstractTest {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private FinderService			finderService;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	/**
	 * This test reefer to use case 17.2
	 * here we're going to test the hackers's finder
	 * Four positives
	 * Six negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void saveFinderDriver() throws ParseException {

		final Collection<Position> position1 = new ArrayList<Position>();
		final Collection<Position> position2 = new ArrayList<Position>();
		final Collection<Position> position3 = new ArrayList<Position>();
		final Collection<Position> position4 = new ArrayList<Position>();

		position1.add(this.positionService.findOne(9590));
		position2.add(this.positionService.findOne(9592));
		position2.add(this.positionService.findOne(9590));
		position4.addAll(this.positionService.getAllPositionsFiltered());
		position4.remove(this.positionService.findOne(9602));
		position4.remove(this.positionService.findOne(9603));
		position4.remove(this.positionService.findOne(9604));

		final Date date1 = this.FORMAT.parse("1000/11/11 01:00:00");
		final Date date2 = this.FORMAT.parse("3000/11/11 01:00:00");
		final Date date3 = this.FORMAT.parse("2021/11/11 01:00:00");

		final Object testingData[][] = {

			//Correct
			{
				/**
				 * a) 17.2: hackers have finders
				 * b) Positive
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "Phishing", this.finderService.findOne(9489), date1, date2, 0.0, position1, null
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Positive
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "", this.finderService.findOne(9489), date1, date3, 0.0, position2, null
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Positive
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "", this.finderService.findOne(9489), date1, date2, 8000.0, position3, null
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Positive
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "", this.finderService.findOne(9489), date1, date2, 0.0, position4, null
			},

			//Incorrect user
			{
				/**
				 * a) 17.2: hackers have finders
				 * b) Hacker can't using the finder of another hacker
				 * c) 22.22%
				 * d) 50%
				 * 
				 */
				"hacker1", "", this.finderService.findOne(9489), date1, date2, 0.0, position4, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(company)
				 * c) 7.4%
				 * d) 50%
				 * 
				 */
				"company0", "Drive", this.finderService.findOne(9489), date1, date2, 0.0, position1, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(administrator)
				 * c) 7.4%
				 * d) 50%
				 * 
				 */
				"admin0", "", this.finderService.findOne(9489), date1, date3, 0.0, position2, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(not logged)
				 * c) 7.4%
				 * d) 50%
				 * 
				 */
				null, "", this.finderService.findOne(9489), date1, date2, 8000.0, position3, IllegalArgumentException.class
			},

			//Incorrect data
			{
				/**
				 * a) 17.2: hackers have finders
				 * b) Can't find a html code
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "<script><script>", this.finderService.findOne(9489), date1, date2, 0.0, position1, ConstraintViolationException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Can't find a negative minimum salary
				 * c) 100%
				 * d) 50%
				 * 
				 */
				"hacker0", "", this.finderService.findOne(9489), date1, date2, -8000.0, position3, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveFinderTemplate((String) testingData[i][0], (String) testingData[i][1], (Finder) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (Double) testingData[i][5], (Collection<Position>) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void saveFinderTemplate(final String beanName, final String keyword, Finder finder, final Date minimumDeadline, final Date maximumDeadline, final Double minimumSalary, final Collection<Position> resultsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			finder.setKeyWord(keyword);
			finder.setMinimumDeadLine(minimumDeadline);
			finder.setMaximumDeadLine(maximumDeadline);
			finder.setMinimumSalary(minimumSalary);

			finder = this.finderService.save(finder);
			this.finderService.flush();

			Assert.isTrue(resultsExpected.containsAll(finder.getPositions()) && resultsExpected.size() == finder.getPositions().size());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	/**
	 * This test reefer to use case 17.2
	 * Let's test the cleanliness of the finders
	 * Four positives
	 * Six negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void clearFinderDriver() throws ParseException {

		final Collection<Position> position = new ArrayList<Position>();

		position.addAll(this.positionService.getAllPositionsFiltered());
		position.remove(this.positionService.findOne(9602));
		position.remove(this.positionService.findOne(9603));
		position.remove(this.positionService.findOne(9604));

		final Object testingData[][] = {

			//Correct
			{
				/**
				 * a) 17.2: hackers have finders
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"hacker0", this.finderService.findOne(9489), position, null
			},

			//Incorrect user
			{
				/**
				 * a) 17.2: hackers have finders
				 * b) Hacker can't using the finder of another hacker
				 * c) 43.47%
				 * d)
				 * 
				 */
				"hacker1", this.finderService.findOne(9489), position, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(company)
				 * c) 8.69%
				 * d)
				 * 
				 */
				"company0", this.finderService.findOne(9489), position, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(administrator)
				 * c) 8.69%
				 * d)
				 * 
				 */
				"admin0", this.finderService.findOne(9489), position, IllegalArgumentException.class
			}, {
				/**
				 * a) 17.2: hackers have finders
				 * b) Must be a hacker(not logged)
				 * c) 8.69%
				 * d)
				 * 
				 */
				null, this.finderService.findOne(9489), position, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.clearFinderTemplate((String) testingData[i][0], (Finder) testingData[i][1], (Collection<Position>) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void clearFinderTemplate(final String beanName, Finder finder, final Collection<Position> resultsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			finder = this.finderService.clear(finder);
			this.finderService.flush();

			Assert.isTrue(resultsExpected.containsAll(finder.getPositions()) && resultsExpected.size() == finder.getPositions().size());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
