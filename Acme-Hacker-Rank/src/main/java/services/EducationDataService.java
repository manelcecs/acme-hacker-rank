
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
	private EducationDataRepository	educationDatarepository;


	public EducationData create() {
		final EducationData res = new EducationData();

		return res;
	}

	public EducationData save(final EducationData educationData) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		Assert.isTrue(educationData.getStartDate().before(educationData.getEndDate()));
		Assert.isTrue(educationData.getEndDate().after(educationData.getStartDate()));

		return this.educationDatarepository.save(educationData);
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
		return this.educationDatarepository.findOne(educationDataId);
	}

	public Collection<EducationData> findAllCurricula(final Curricula curricula) {
		return this.educationDatarepository.findAllCurricula(curricula.getId());
	}

}
