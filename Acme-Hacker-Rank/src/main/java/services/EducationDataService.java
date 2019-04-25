
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService {

	@Autowired
	private EducationDataRepository	educationDataRepository;

	@Autowired
	private CurriculaService		curriculaService;


	public EducationData create(final int curriculaId) {
		final EducationData res = new EducationData();
		final Curricula c = this.curriculaService.findOne(curriculaId);
		res.setCurricula(c);
		return res;
	}

	public EducationData save(final EducationData educationData) {
		Assert.isTrue(educationData != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		if (educationData.getId() != 0)
			Assert.isTrue(!educationData.getCurricula().getCopy());

		if (educationData.getEndDate() != null) {
			Assert.isTrue(educationData.getStartDate().before(educationData.getEndDate()));
			Assert.isTrue(educationData.getEndDate().after(educationData.getStartDate()));
		}
		return this.educationDataRepository.save(educationData);
	}

	public void createCopy(final Curricula curricula, final EducationData educationData) {

		final EducationData res = new EducationData();

		res.setCurricula(curricula);
		res.setDegree(educationData.getDegree());
		res.setInstitution(educationData.getInstitution());
		res.setEndDate(educationData.getEndDate());
		res.setStartDate(educationData.getStartDate());
		res.setMark(educationData.getMark());

		this.save(res);

	}

	public EducationData findOne(final int educationDataId) {
		return this.educationDataRepository.findOne(educationDataId);
	}

	public Collection<EducationData> findAllCurricula(final Curricula curricula) {
		return this.educationDataRepository.findAllCurricula(curricula.getId());
	}

	public void delete(final EducationData educationData) {
		this.educationDataRepository.delete(educationData);
	}

	public void delete(final Collection<EducationData> educationData) {
		this.educationDataRepository.delete(educationData);
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

}
