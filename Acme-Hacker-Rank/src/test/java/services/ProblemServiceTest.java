
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private PositionService	positionService;


	@Override
	@Before
	public void setUp() {
		this.unauthenticate();
	}

	/**
	 * This test reefer to use case 9.2
	 * here we're going to test the create/edit of problems
	 * Two positives
	 * Fourteen negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void saveProblemDriver() {
		final Object testingData[][] = {

			//Correct create
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Positive
				 * c) 66.66%
				 * d)
				 * 
				 */
				"company0", new Problem(), "title", "statement", "hint", null, true, 9590, null
			},
			//Create a new problem
			//Correct edit
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), "title", "statement", "hint", null, true, 9590, null
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(administrator)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"admin", new Problem(), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(hacker)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"hacker1", new Problem(), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(not logged)
				 * c) 22.22%
				 * d)
				 * 
				 */
				null, new Problem(), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			},

			//Blank title
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Title can't be blank
				 * c) 66.66%
				 * d)
				 * 
				 */
				"company0", new Problem(), "", "statement", "hint", null, true, 9590, ConstraintViolationException.class
			},

			//Blank statement
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Statement can't be blank
				 * c) 66.66%
				 * d)
				 * 
				 */
				"company0", new Problem(), "title", "", "hint", null, true, 9590, ConstraintViolationException.class
			},

			//Null position
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Position can't be null
				 * c) 66.66%
				 * d)
				 * 
				 */
				"company0", new Problem(), "title", "statement", "hint", null, true, null, NullPointerException.class
			},

			//Edit a problem
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) A company can't edit a problem from another company
				 * c) 88.88%
				 * d)
				 * 
				 */
				"company3", this.problemService.findOne(9605), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(administrator)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"admin", this.problemService.findOne(9605), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(hacker)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"hacker0", this.problemService.findOne(9605), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be a company(not logged)
				 * c) 22.22%
				 * d)
				 * 
				 */
				null, this.problemService.findOne(9605), "title", "statement", "hint", null, true, 9590, IllegalArgumentException.class
			},

			//Not in draft mode
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Must be in draft mode
				 * c) 77.77%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), "title", "statement", "hint", null, false, 9590, IllegalArgumentException.class
			},

			//Blank title
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Title can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), "", "statement", "hint", null, true, 9590, ConstraintViolationException.class
			},

			//Blank statement
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Statement can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), "title", "", "hint", null, true, 9590, ConstraintViolationException.class
			},

			//Null position
			{
				/**
				 * a) 9.2: companies can create/edit their database of problems
				 * b) Position can't be null
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), "title", "statement", "hint", null, true, null, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.saveProblemTemplate((String) testingData[i][0], (Problem) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Collection<String>) testingData[i][5], (Boolean) testingData[i][6],
				(Integer) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void saveProblemTemplate(final String beanName, final Problem problem, final String title, final String statement, final String hint, final Collection<String> attachments, final Boolean draft, final Integer position, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			problem.setTitle(title);
			problem.setStatement(statement);
			problem.setHint(hint);
			problem.setDraft(draft);
			if (position != null)
				problem.setPosition(this.positionService.findOne(position));
			this.problemService.save(problem);
			this.problemService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 9.2
	 * here we're going to test delete of problems
	 * One positive
	 * Five negatives
	 */
	@Test
	public void deleteProblemDriver() {
		final Object testingData[][] = {

			{
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), null
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(administrator)
				 * c) 28.57%
				 * d)
				 * 
				 */
				"admin", this.problemService.findOne(9605), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(hacker)
				 * c) 28.57%
				 * d)
				 * 
				 */
				"hacker1", this.problemService.findOne(9605), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(not logged)
				 * c) 28.57%
				 * d)
				 * 
				 */
				null, this.problemService.findOne(9605), IllegalArgumentException.class
			},
			//problem in final mode
			{
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Problem must be in draft mode
				 * c) 85.61%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9606), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) A company can't delete a problem of another company
				 * c) 71.42%
				 * d)
				 * 
				 */
				"company1", this.problemService.findOne(9605), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteProblemTemplate((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteProblemTemplate(final String beanName, final Problem problem, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.problemService.delete(problem);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 9.2
	 * here we're going to test the change mode of problems
	 * One positives
	 * Five negatives
	 */
	@Test
	public void changeDraftProblemDriver() {
		final Object testingData[][] = {

			{
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9605), null
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(administrator)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"admin", this.problemService.findOne(9605), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(hacker)
				 * c) 22.22%
				 * d)
				 * 
				 */
				"hacker1", this.problemService.findOne(9605), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Must be a company(not logged)
				 * c) 22.22%
				 * d)
				 * 
				 */
				null, this.problemService.findOne(9605), IllegalArgumentException.class
			},
			//problem in final mode
			{
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) Problem must be in draft mode
				 * c) 44.44%
				 * d)
				 * 
				 */
				"company0", this.problemService.findOne(9606), IllegalArgumentException.class
			}, {
				/**
				 * a) 9.2: companies can delete their database of problems
				 * b) A company can't delete a problem of another company
				 * c) 77.77%
				 * d)
				 * 
				 */
				"company1", this.problemService.findOne(9605), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeDraftProblemTemplate((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void changeDraftProblemTemplate(final String beanName, final Problem problem, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.problemService.changeDraft(problem);
			this.problemService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
