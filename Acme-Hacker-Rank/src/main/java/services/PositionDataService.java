
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;


	public PositionData create() {
		final PositionData res = new PositionData();

		return res;
	}

	public PositionData save(final PositionData positionData) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		return this.positionDataRepository.save(positionData);
	}

	public PositionData findOne(final int positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}

	public Collection<PositionData> findAllCurricula(final Curricula curricula) {
		return this.positionDataRepository.findAllCurricula(curricula.getId());
	}

	public void createCopy(final Curricula curricula, final PositionData positionData) {
		final PositionData res = new PositionData();

		res.setCurricula(curricula);
		res.setDescription(positionData.getDescription());
		res.setEndDate(positionData.getEndDate());
		res.setStartDate(positionData.getStartDate());
		res.setTitle(positionData.getTitle());

		this.save(res);
	}

}
