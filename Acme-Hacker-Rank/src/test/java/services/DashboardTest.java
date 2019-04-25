
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Company;
import domain.Hacker;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private PositionService			positionService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private HackerService			hackerService;


	@Override
	@Before
	public void setUp() {
		this.unauthenticate();
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to positions for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardPositionDriver() {

		final Collection<Company> companies = new ArrayList<Company>();
		companies.add(this.companyService.findOne(9440));
		companies.add(this.companyService.findOne(9447));
		companies.add(this.companyService.findOne(9454));
		companies.add(this.companyService.findOne(9461));
		companies.add(this.companyService.findOne(9468));
		companies.add(this.companyService.findOne(9475));

		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(hacker)
				 * c) 50%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardPositionTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Company>) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void dashboardPositionTemplate(final String beanName, final Double avgOfPositionsPerCompanyExpected, final Integer maximumOfPositionsPerCompanyExpected, final Integer minimumOfPositionsPerCompanyTestExpected,
		final Double sDOfPositionsPerCompanyExpected, final Collection<Company> companiesWithMoreOffersOfPositionsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfPositionsPerCompany = this.administratorService.getAvgOfPositionsPerCompany();
			final Integer maximumOfPositionsPerCompany = this.administratorService.getMaximumOfPositionsPerCompany();
			final Integer minimumOfPositionsPerCompany = this.administratorService.getMinimumOfPositionsPerCompany();
			final Double sDOfPositionsPerCompany = this.administratorService.getSDOfPositionsPerCompany();
			final Collection<Company> companiesWithMoreOffersOfPositions = this.companyService.getCompaniesWithMoreOffersOfPositions();

			Assert.isTrue(companiesWithMoreOffersOfPositionsExpected.containsAll(companiesWithMoreOffersOfPositions) && companiesWithMoreOffersOfPositionsExpected.size() == companiesWithMoreOffersOfPositions.size());
			Assert.isTrue(avgOfPositionsPerCompany.equals(avgOfPositionsPerCompanyExpected) && maximumOfPositionsPerCompany.equals(maximumOfPositionsPerCompanyExpected) && minimumOfPositionsPerCompany.equals(minimumOfPositionsPerCompanyTestExpected)
				&& sDOfPositionsPerCompany.equals(sDOfPositionsPerCompanyExpected));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to applications for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardApplicationDriver() {

		final Collection<Hacker> hackers = new ArrayList<Hacker>();
		hackers.add(this.hackerService.findOne(9496));
		hackers.add(this.hackerService.findOne(9504));
		hackers.add(this.hackerService.findOne(9512));
		hackers.add(this.hackerService.findOne(9520));
		hackers.add(this.hackerService.findOne(9528));
		hackers.add(this.hackerService.findOne(9536));
		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(hacker)
				 * c) 50%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, hackers, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, hackers, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.8571, 1, 0, 0.3499, hackers, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, hackers, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardApplicationTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Hacker>) testingData[i][5],
				(Class<?>) testingData[i][6]);
	}
	protected void dashboardApplicationTemplate(final String beanName, final Double avgOfApplicationsPerHackerExpected, final Integer maximumOfApplicationsPerHackerExpected, final Integer minimumOfApplicationsPerHackerExpected,
		final Double sDOfApplicationsPerHackerExpected, final Collection<Hacker> hackersWithMoreApplicationsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Double avgOfApplicationsPerHacker = this.administratorService.getAvgOfApplicationsPerHacker();
			final Integer maximumOfApplicationsPerHacker = this.administratorService.getMaximumOfApplicationsPerHacker();
			final Integer minimumOfApplicationsPerHacker = this.administratorService.getMinimumOfApplicationsPerHacker();
			final Double sDOfApplicationsPerHacker = this.administratorService.getSDOfApplicationsPerHacker();
			final Collection<Hacker> hackersWithMoreApplications = this.hackerService.getHackersWithMoreApplications();

			Assert.isTrue(hackersWithMoreApplicationsExpected.containsAll(hackersWithMoreApplications) && hackersWithMoreApplicationsExpected.size() == hackersWithMoreApplications.size());
			Assert.isTrue(avgOfApplicationsPerHacker.equals(avgOfApplicationsPerHackerExpected) && maximumOfApplicationsPerHacker.equals(maximumOfApplicationsPerHackerExpected)
				&& minimumOfApplicationsPerHacker.equals(minimumOfApplicationsPerHackerExpected) && sDOfApplicationsPerHacker.equals(sDOfApplicationsPerHackerExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to applications for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardSalaryDriver() {

		final Collection<Position> bestPositions = new ArrayList<Position>();
		bestPositions.add(this.positionService.findOne(9599));
		bestPositions.add(this.positionService.findOne(9598));
		bestPositions.add(this.positionService.findOne(9597));
		bestPositions.add(this.positionService.findOne(9596));
		bestPositions.add(this.positionService.findOne(9595));
		bestPositions.add(this.positionService.findOne(9593));

		final Collection<Position> worstPositions = new ArrayList<Position>();
		worstPositions.add(this.positionService.findOne(9592));
		worstPositions.add(this.positionService.findOne(9600));
		worstPositions.add(this.positionService.findOne(9601));
		worstPositions.add(this.positionService.findOne(9602));
		worstPositions.add(this.positionService.findOne(9603));
		worstPositions.add(this.positionService.findOne(9604));

		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(hacker)
				 * c) 50%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardSalaryTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Position>) testingData[i][5],
				(Collection<Position>) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void dashboardSalaryTemplate(final String beanName, final Double avgOfSalariesOfferedExpected, final Integer maximumOfSalariesOfferedExpected, final Integer minimumOfSalariesOfferedExpected, final Double sDOfSalariesOfferedExpected,
		final Collection<Position> positionsWithTheBestSalaryExpected, final Collection<Position> positionsWithTheWorstSalaryExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfSalariesOffered = this.administratorService.getAvgOfSalariesOffered();
			final Integer maximumOfSalariesOffered = this.administratorService.getMaximumOfSalariesOffered();
			final Integer minimumOfSalariesOffered = this.administratorService.getMinimumOfSalariesOffered();
			final Double sDOfSalariesOffered = this.administratorService.getSDOfSalariesOffered();

			final Collection<Position> positionsWithTheBestSalary = this.positionService.getPositionsWithTheBestSalary();
			final Collection<Position> positionsWithTheWorstSalary = this.positionService.getPositionsWithTheWorstSalary();

			Assert.isTrue(positionsWithTheWorstSalary.containsAll(positionsWithTheWorstSalaryExpected) && positionsWithTheWorstSalaryExpected.size() == positionsWithTheWorstSalary.size());
			Assert.isTrue(positionsWithTheBestSalary.containsAll(positionsWithTheBestSalaryExpected) && positionsWithTheBestSalaryExpected.size() == positionsWithTheBestSalary.size());
			Assert.isTrue(avgOfSalariesOffered.equals(avgOfSalariesOfferedExpected) && maximumOfSalariesOffered.equals(maximumOfSalariesOfferedExpected) && minimumOfSalariesOffered.equals(minimumOfSalariesOfferedExpected)
				&& sDOfSalariesOffered.equals(sDOfSalariesOfferedExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 18.1
	 * here we're going to test the dashboard metrics related to curricula for the administrator
	 * One positive
	 * Three negatives
	 */
	@Test
	public void dashboardCurriculaDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(hacker)
				 * c) 50%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardCurriculaTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardCurriculaTemplate(final String beanName, final Double avgOfCurriculaPerHackerExpected, final Integer maximumOfCurriculaPerHackerExpected, final Integer minimumOfCurriculaPerHackerExpected,
		final Double sDOfCurriculaPerHackerExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Double avgOfCurriculaPerHacker = this.administratorService.getAvgOfCurriculaPerHacker();
			final Integer maximumOfCurriculaPerHacker = this.administratorService.getMaximumOfCurriculaPerHacker();
			final Integer minimumOfCurriculaPerHacker = this.administratorService.getMinimumOfCurriculaPerHacker();
			final Double sDOfCurriculaPerHacker = this.administratorService.getSDOfCurriculaPerHacker();

			Assert.isTrue(avgOfCurriculaPerHacker.equals(avgOfCurriculaPerHackerExpected) && maximumOfCurriculaPerHacker.equals(maximumOfCurriculaPerHackerExpected) && minimumOfCurriculaPerHacker.equals(minimumOfCurriculaPerHackerExpected)
				&& sDOfCurriculaPerHacker.equals(sDOfCurriculaPerHackerExpected));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 18.1
	 * here we're going to test the dashboard metrics related to finders for the administrator
	 * One positive
	 * Three negatives
	 */
	@Test
	public void dashboardFinderDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(hacker)
				 * c) 50%
				 * d)
				 * 
				 */
				this.hackerService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardFinderTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void dashboardFinderTemplate(final String beanName, final Double avgOfResultsInFindersExpected, final Integer maximumOfResultsInFindersExpected, final Integer minimumOfResultsInFindersExpected, final Double sDOfResultsInFindersExpected,
		final Double ratioOfEmptyVsNotEmptyFindersExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfResultsInFinders = this.administratorService.getAvgOfResultsInFinders();
			final Integer maximumOfResultsInFinders = this.administratorService.getMaximumOfResultsInFinders();
			final Integer minimumOfResultsInFinders = this.administratorService.getMinimumOfResultsInFinders();
			final Double sDOfResultsInFinders = this.administratorService.getSDOfResultsInFinders();
			Double ratioOfEmptyVsNotEmptyFinders = this.administratorService.getRatioOfEmptyVsNotEmptyFinders();
			if (ratioOfEmptyVsNotEmptyFinders == null)
				ratioOfEmptyVsNotEmptyFinders = 0.0;
			Assert.isTrue(avgOfResultsInFinders.equals(avgOfResultsInFindersExpected) && maximumOfResultsInFinders.equals(maximumOfResultsInFindersExpected) && minimumOfResultsInFinders.equals(minimumOfResultsInFindersExpected)
				&& sDOfResultsInFinders.equals(sDOfResultsInFindersExpected) && ratioOfEmptyVsNotEmptyFinders.equals(ratioOfEmptyVsNotEmptyFindersExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
