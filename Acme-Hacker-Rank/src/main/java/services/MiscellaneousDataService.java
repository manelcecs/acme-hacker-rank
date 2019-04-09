
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import utiles.AuthorityMethods;
import domain.Curricula;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	@Autowired
	private MiscellaneousDataRepository	miscellaneousRepository;


	public MiscellaneousData create() {
		final MiscellaneousData misc = new MiscellaneousData();

		return misc;
	}

	public MiscellaneousData save(final MiscellaneousData miscData) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("HACKER"));

		return this.miscellaneousRepository.save(miscData);
	}

	public MiscellaneousData findOne(final int miscellaneousData) {
		return this.miscellaneousRepository.findOne(miscellaneousData);
	}

	public Collection<MiscellaneousData> findAllCurricula(final Curricula curricula) {
		return this.miscellaneousRepository.findAllCurricula(curricula.getId());
	}

	public void createCopy(final Curricula curricula, final MiscellaneousData misc) {
		final MiscellaneousData res = new MiscellaneousData();

		res.setAttachments(misc.getAttachments());
		res.setCurricula(curricula);
		res.setText(misc.getText());

		this.save(res);
	}

}
