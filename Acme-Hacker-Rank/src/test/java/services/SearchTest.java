
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SearchTest extends AbstractTest {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CompanyService			companyService;


	/**
	 * This test reefer to use case 7.4
	 * here we're going to test the search of positions by keyword
	 * Four positive
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void searchDriver() {

		final Collection<Position> position1 = new ArrayList<Position>();
		final Collection<Position> position2 = new ArrayList<Position>();
		final Collection<Position> position3 = new ArrayList<Position>();
		final Collection<Position> position4 = new ArrayList<Position>();

		position1.add(this.positionService.findOne(9592));
		position2.add(this.positionService.findOne(9598));
		position2.add(this.positionService.findOne(9599));
		position4.addAll(this.positionService.getAllPositionsFiltered());
		final Object testingData[][] = {
			{
				/**
				 * a) 7.4: search positions by keyword
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), "Drive", position1, null
			}, {
				/**
				 * a) 7.4: search positions by keyword
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), "Reparación de servidores.", position2, null
			}, {
				/**
				 * a) 7.4: search positions by keyword
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), "DBA", position3, null
			}, {
				/**
				 * a) 7.4: search positions by keyword
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				null, "", position4, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchTemplate((String) testingData[i][0], (String) testingData[i][1], (Collection<Position>) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void searchTemplate(final String beanName, final String keyword, final Collection<Position> resultsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Collection<Position> results = this.positionService.getFilterPositionsByKeyword(keyword);
			Assert.isTrue(resultsExpected.containsAll(results) && resultsExpected.size() == results.size());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
