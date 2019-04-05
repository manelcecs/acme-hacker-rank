
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;
import domain.Hacker;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	@Autowired
	private UserAccountRepository	accountRepository;
	@Autowired
	private HackerRepository		hackerRepository;
	//@Autowired
	//private AdminConfigRepository	adminConfigRepository;
	@Autowired
	private Validator				validator;


	public Hacker create() {
		final Hacker res = new Hacker();
		//TODO: añadir finder
		//TODO: añadir cajas de mensajes
		return res;
	}

	public Hacker save(final Hacker hacker) {
		Assert.isTrue(hacker != null);
		Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());

		if (hacker.getId() == 0) {
			final UserAccount userAccount = hacker.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			hacker.setUserAccount(finalAccount);
		} else
			Assert.isTrue(!hacker.getBanned());

		final Hacker res = this.hackerRepository.save(hacker);

		return res;

	}

	public void flush() {
		this.hackerRepository.flush();
	}

	public Hacker findOne(final int hackerId) {
		return this.hackerRepository.findOne(hackerId);
	}

	public Hacker findByPrincipal(final UserAccount principal) {
		return this.hackerRepository.findByPrincipal(principal.getId());
	}

	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) {
		final Hacker result;
		result = this.create();

		final UserAccount account = hackerForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(hackerForm.getAddress());
		result.setEmail(hackerForm.getEmail());
		result.setName(hackerForm.getName());
		//TODO:
		//result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.hackerConfigService.getAdminConfig().getCountryCode(), hackerForm.getPhoneNumber()));
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setPhoto(hackerForm.getPhoto());
		final String surnames[] = hackerForm.getSurnames().split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		result.setSurnames(surNames);

		result.setCreditCard(hackerForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	//DASHBOARD---------------------------------------------------------

	public Collection<Hacker> getHackersWithMoreApplications() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.hackerRepository.getHackersWithMoreApplications();
	}

}
