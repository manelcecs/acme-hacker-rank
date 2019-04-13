
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PositionData;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository			curriculaRepository;
	@Autowired
	private HackerService				hackerService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private EducationDataService		educationDataService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private PositionDataService			positionDataService;


	public Curricula create() {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));
		final Curricula res = new Curricula();
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());
		res.setCopy(false);
		res.setHacker(hacker);

		return res;
	}

	public Curricula createCopy(final Curricula curricula) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(curricula.getHacker().getId() == hacker.getId());

		final Curricula newCur = new Curricula();

		newCur.setHacker(hacker);
		newCur.setCopy(true);

		final Curricula res = this.save(newCur);

		this.personalDataService.createCopy(this.personalDataService.findByCurricula(curricula), res);

		for (final EducationData edu : this.educationDataService.findAllCurricula(curricula))
			this.educationDataService.createCopy(curricula, edu);

		for (final MiscellaneousData misc : this.miscellaneousDataService.findAllCurricula(curricula))
			this.miscellaneousDataService.createCopy(curricula, misc);

		for (final PositionData pos : this.positionDataService.findAllCurricula(curricula))
			this.positionDataService.createCopy(curricula, pos);

		//TODO: asegurar unicidad

		return res;
	}

	public Curricula save(final Curricula curricula) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		final Hacker hacker = this.hackerService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(curricula.getHacker().getId() == hacker.getId());

		final Curricula res = this.curriculaRepository.save(curricula);

		return res;

	}

	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Collection<Curricula> findAllNoCopy(final Hacker hacker) {
		return this.curriculaRepository.findAllNoCopy(hacker.getId());
	}

	public Collection<Curricula> findAllApplication(final int applicationId) {
		return this.curriculaRepository.findAllApplication(applicationId);
	}

	public Collection<Curricula> getCurriculasOfHacker(final int idHacker) {
		return this.curriculaRepository.getCurriculasOfHacker(idHacker);
	}
}