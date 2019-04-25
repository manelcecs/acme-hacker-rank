
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Finder;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private Validator				validator;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	public Finder save(final Finder finder) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();

		Assert.notNull(finder);
		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + LocalDateTime.now().getMinuteOfHour() + ":"
			+ DATETIMENOW.getSecondOfMinute());

		Assert.isTrue(this.hackerService.findByPrincipal(LoginService.getPrincipal()).getFinder().getId() == finder.getId());

		String keyWord = finder.getKeyWord();
		Date minimumdeadline = finder.getMinimumDeadLine();
		Date maximumDeadline = finder.getMaximumDeadLine();
		Double minimumSalary = finder.getMinimumSalary();
		if (keyWord == null)
			keyWord = "";
		if (minimumdeadline == null)
			minimumdeadline = this.FORMAT.parse("0001/01/01 01:00:00");
		if (maximumDeadline == null)
			maximumDeadline = this.FORMAT.parse("9999/01/01 01:00:00");
		if (minimumSalary == null)
			minimumSalary = 0.0;

		final Collection<Position> results = this.positionService.getFilterPositionsByFinder(keyWord, minimumdeadline, maximumDeadline, minimumSalary);

		List<Position> returnResults = new ArrayList<Position>();
		returnResults.addAll(results);
		final Integer maxFinderResults = this.adminConfigService.getAdminConfig().getResultsFinder();
		if (returnResults.size() > maxFinderResults)
			returnResults = returnResults.subList(0, maxFinderResults);
		finder.setPositions(returnResults);
		finder.setLastUpdate(actual);
		return this.finderRepository.save(finder);
	}

	public Finder clear(final Finder finder) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();

		Assert.notNull(finder);

		finder.setMinimumDeadLine(null);
		finder.setKeyWord(null);
		finder.setMaximumDeadLine(null);
		finder.setMinimumSalary(null);

		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + LocalDateTime.now().getMinuteOfHour() + ":"
			+ DATETIMENOW.getSecondOfMinute());

		Assert.isTrue(this.hackerService.findByPrincipal(LoginService.getPrincipal()).getFinder().getId() == finder.getId());

		final String keyWord = "";
		final Date minimunDeadline = this.FORMAT.parse("0001/01/01 01:00:00");
		final Date maximumDeadline = this.FORMAT.parse("9999/01/01 01:00:00");
		final Double minimumSalary = 0.0;

		final Collection<Position> results = this.positionService.getFilterPositionsByFinder(keyWord, minimunDeadline, maximumDeadline, minimumSalary);
		List<Position> returnResults = new ArrayList<Position>();
		returnResults.addAll(results);
		final Integer maxFinderResults = this.adminConfigService.getAdminConfig().getResultsFinder();
		if (returnResults.size() > maxFinderResults)
			returnResults = returnResults.subList(0, maxFinderResults);
		finder.setPositions(returnResults);
		finder.setLastUpdate(actual);
		return this.finderRepository.save(finder);
	}
	public Finder findOne(final int finderId) {
		Assert.notNull(finderId);
		return this.finderRepository.findOne(finderId);
	}

	public Boolean cacheFinder(final Finder finderA, final Finder finderB) throws ParseException {
		Boolean result;
		final LocalDateTime DATETIMENOW = LocalDateTime.now();

		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + DATETIMENOW.getMinuteOfHour() + ":" + DATETIMENOW.getSecondOfMinute());
		final Calendar cal = Calendar.getInstance();
		cal.setTime(finderA.getLastUpdate());
		cal.set(Calendar.MILLISECOND, (cal.get(Calendar.MILLISECOND) + (this.adminConfigService.getAdminConfig().getCacheFinder() * 3600000)));
		final Date expirationDate = cal.getTime();

		Boolean keyWord = true;
		if (finderA.getKeyWord() != null && finderB.getKeyWord() == null)
			keyWord = finderA.getKeyWord().isEmpty();
		if (finderA.getKeyWord() == null && finderB.getKeyWord() != null)
			keyWord = finderB.getKeyWord().isEmpty();
		if (finderA.getKeyWord() != null && finderB.getKeyWord() != null)
			keyWord = finderA.getKeyWord().equals(finderB.getKeyWord());

		Boolean minimumDeadline = false;
		if (finderA.getMinimumDeadLine() == null && finderB.getMinimumDeadLine() == null)
			minimumDeadline = true;
		if (finderA.getMinimumDeadLine() != null && finderB.getMinimumDeadLine() != null)
			minimumDeadline = finderA.getMinimumDeadLine().compareTo(finderB.getMinimumDeadLine()) == 0;

		Boolean maximumDeadline = false;
		if (finderA.getMaximumDeadLine() == null && finderB.getMaximumDeadLine() == null)
			maximumDeadline = true;
		if (finderA.getMaximumDeadLine() != null && finderB.getMaximumDeadLine() != null)
			maximumDeadline = finderA.getMaximumDeadLine().compareTo(finderB.getMaximumDeadLine()) == 0;

		Boolean minimumSalary = false;
		if (finderA.getMinimumSalary() == null && finderB.getMinimumSalary() == null)
			minimumSalary = true;
		if (finderA.getMinimumSalary() != null && finderB.getMinimumSalary() != null)
			minimumSalary = finderA.getMinimumSalary() - finderB.getMinimumSalary() == 0;

		result = keyWord && minimumDeadline && maximumDeadline && actual.before(expirationDate) && minimumSalary;
		return result;
	}
	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		final Finder result = this.finderRepository.findOne(this.hackerService.findByPrincipal(LoginService.getPrincipal()).getFinder().getId());

		finder.setId(result.getId());
		finder.setVersion(result.getVersion());
		finder.setPositions(result.getPositions());
		finder.setLastUpdate(result.getLastUpdate());
		this.validator.validate(finder, binding);

		return finder;
	}

	public Finder create() throws ParseException {
		final Finder finder = new Finder();

		final Date actual = this.FORMAT.parse("0001/01/01 01:00:00");
		finder.setLastUpdate(actual);
		return finder;
	}
	public Finder generateNewFinder() throws ParseException {

		final Finder finder = this.create();
		Finder res;
		res = this.finderRepository.save(finder);
		return res;
	}

	public void flush() {
		this.finderRepository.flush();
	}

}
