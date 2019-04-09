
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CompanyService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;


	@RequestMapping(value = "/administrator/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("administrator/dashboard");

		final Double avgOfPositionsPerCompany = this.administratorService.getAvgOfPositionsPerCompany();
		if (avgOfPositionsPerCompany != null)
			result.addObject("avgOfPositionsPerCompany", avgOfPositionsPerCompany);
		else
			result.addObject("avgOfPositionsPerCompany", 0.0);

		final Integer minimumOfPositionsPerCompany = this.administratorService.getMinimumOfPositionsPerCompany();
		if (minimumOfPositionsPerCompany != null)
			result.addObject("minimumOfPositionsPerCompany", minimumOfPositionsPerCompany);
		else
			result.addObject("minimumOfPositionsPerCompany", 0);

		final Integer maximumOfPositionsPerCompany = this.administratorService.getMaximumOfPositionsPerCompany();
		if (maximumOfPositionsPerCompany != null)
			result.addObject("maximumOfPositionsPerCompany", maximumOfPositionsPerCompany);
		else
			result.addObject("maximumOfPositionsPerCompany", 0);

		final Double sDOfPositionsPerCompany = this.administratorService.getSDOfPositionsPerCompany();
		if (sDOfPositionsPerCompany != null)
			result.addObject("sDOfPositionsPerCompany", sDOfPositionsPerCompany);
		else
			result.addObject("sDOfPositionsPerCompany", 0.0);

		final Double avgOfApplicationsPerHacker = this.administratorService.getAvgOfApplicationsPerHacker();
		if (avgOfApplicationsPerHacker != null)
			result.addObject("avgOfApplicationsPerHacker", avgOfApplicationsPerHacker);
		else
			result.addObject("avgOfApplicationsPerHacker", 0.0);

		final Integer minimumOfApplicationsPerHacker = this.administratorService.getMinimumOfApplicationsPerHacker();
		if (minimumOfApplicationsPerHacker != null)
			result.addObject("minimumOfApplicationsPerHacker", minimumOfApplicationsPerHacker);
		else
			result.addObject("minimumOfApplicationsPerHacker", 0);

		final Integer maximumOfApplicationsPerHacker = this.administratorService.getMaximumOfApplicationsPerHacker();
		if (maximumOfApplicationsPerHacker != null)
			result.addObject("maximumOfApplicationsPerHacker", maximumOfApplicationsPerHacker);
		else
			result.addObject("maximumOfApplicationsPerHacker", 0);

		final Double sDOfApplicationsPerHacker = this.administratorService.getSDOfApplicationsPerHacker();
		if (sDOfApplicationsPerHacker != null)
			result.addObject("sDOfApplicationsPerHacker", sDOfApplicationsPerHacker);
		else
			result.addObject("sDOfApplicationsPerHacker", 0.0);

		final Double avgOfSalariesOffered = this.administratorService.getAvgOfSalariesOffered();
		if (avgOfSalariesOffered != null)
			result.addObject("avgOfSalariesOffered", avgOfSalariesOffered);
		else
			result.addObject("avgOfSalariesOffered", 0.0);

		final Integer minimumOfSalariesOffered = this.administratorService.getMinimumOfSalariesOffered();
		if (minimumOfSalariesOffered != null)
			result.addObject("minimumOfSalariesOffered", minimumOfSalariesOffered);
		else
			result.addObject("minimumOfSalariesOffered", 0);

		final Integer maximumOfSalariesOffered = this.administratorService.getMaximumOfSalariesOffered();
		if (maximumOfSalariesOffered != null)
			result.addObject("maximumOfSalariesOffered", maximumOfSalariesOffered);
		else
			result.addObject("maximumOfSalariesOffered", 0);

		final Double sDOfSalariesOffered = this.administratorService.getSDOfSalariesOffered();
		if (sDOfSalariesOffered != null)
			result.addObject("sDOfSalariesOffered", sDOfSalariesOffered);
		else
			result.addObject("sDOfSalariesOffered", 0.0);

		final Double avgOfCurriculaPerHacker = this.administratorService.getAvgOfCurriculaPerHacker();
		if (avgOfCurriculaPerHacker != null)
			result.addObject("avgOfCurriculaPerHacker", avgOfCurriculaPerHacker);
		else
			result.addObject("avgOfCurriculaPerHacker", 0.0);

		final Integer minimumOfCurriculaPerHacker = this.administratorService.getMinimumOfCurriculaPerHacker();
		if (minimumOfCurriculaPerHacker != null)
			result.addObject("minimumOfCurriculaPerHacker", minimumOfCurriculaPerHacker);
		else
			result.addObject("minimumOfCurriculaPerHacker", 0);

		final Integer maximumOfCurriculaPerHacker = this.administratorService.getMaximumOfCurriculaPerHacker();
		if (maximumOfCurriculaPerHacker != null)
			result.addObject("maximumOfCurriculaPerHacker", maximumOfCurriculaPerHacker);
		else
			result.addObject("maximumOfCurriculaPerHacker", 0);

		final Double sDOfCurriculaPerHacker = this.administratorService.getSDOfCurriculaPerHacker();
		if (sDOfCurriculaPerHacker != null)
			result.addObject("sDOfCurriculaPerHacker", sDOfCurriculaPerHacker);
		else
			result.addObject("sDOfCurriculaPerHacker", 0.0);

		final Double avgOfResultsInFinders = this.administratorService.getAvgOfResultsInFinders();
		if (avgOfResultsInFinders != null)
			result.addObject("avgOfResultsInFinders", avgOfResultsInFinders);
		else
			result.addObject("avgOfResultsInFinders", 0.0);

		final Integer minimumOfResultsInFinders = this.administratorService.getMinimumOfResultsInFinders();
		if (minimumOfResultsInFinders != null)
			result.addObject("minimumOfResultsInFinders", minimumOfResultsInFinders);
		else
			result.addObject("minimumOfResultsInFinders", 0);

		final Integer maximumOfResultsInFinders = this.administratorService.getMaximumOfResultsInFinders();
		if (maximumOfResultsInFinders != null)
			result.addObject("maximumOfResultsInFinders", maximumOfResultsInFinders);
		else
			result.addObject("maximumOfResultsInFinders", 0);

		final Double sDOfResultsInFinders = this.administratorService.getSDOfResultsInFinders();
		if (sDOfResultsInFinders != null)
			result.addObject("sDOfResultsInFinders", sDOfResultsInFinders);
		else
			result.addObject("sDOfResultsInFinders", 0.0);

		final Double ratioOfEmptyVsNotEmptyFinders = this.administratorService.getRatioOfEmptyVsNotEmptyFinders();
		if (ratioOfEmptyVsNotEmptyFinders != null)
			result.addObject("ratioOfEmptyVsNotEmptyFinders", ratioOfEmptyVsNotEmptyFinders);
		else
			result.addObject("ratioOfEmptyVsNotEmptyFinders", 0.0);

		final Collection<Position> positionsWithTheBestSalary = this.positionService.getPositionsWithTheBestSalary();
		result.addObject("positionsWithTheBestSalary", positionsWithTheBestSalary);

		final Collection<Position> positionsWithTheWorstSalary = this.positionService.getPositionsWithTheWorstSalary();
		result.addObject("positionsWithTheWorstSalary", positionsWithTheWorstSalary);

		final Collection<Hacker> hackersWithMoreApplications = this.hackerService.getHackersWithMoreApplications();
		result.addObject("hackersWithMoreApplications", hackersWithMoreApplications);

		final Collection<Company> companiesWithMoreOffersOfPositions = this.companyService.getCompaniesWithMoreOffersOfPositions();
		result.addObject("companiesWithMoreOffersOfPositions", companiesWithMoreOffersOfPositions);

		result.addObject("requestURI", "dashboard/administrator/display.do");

		this.configValues(result);
		return result;
	}
}
