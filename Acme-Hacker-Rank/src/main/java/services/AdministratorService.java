
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private UserAccountRepository	accountRepository;
	@Autowired
	private AdministratorRepository	adminRepository;
	//@Autowired
	//private AdminConfigRepository	adminConfigRepository;
	@Autowired
	private Validator				validator;


	public Administrator create() {
		final Administrator res = new Administrator();

		return res;
	}

	public Administrator save(final Administrator admin) {

		Assert.isTrue(admin != null);
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.ADMINISTRATOR));
		Assert.isTrue(!admin.getBanned());

		if (admin.getId() == 0) {
			final UserAccount userAccount = admin.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			admin.setUserAccount(finalAccount);
		}

		final Administrator res = this.adminRepository.save(admin);

		return res;

	}

	public Administrator findByPrincipal(final int principalId) {
		return this.adminRepository.findByPrincipal(principalId);
	}

	public Administrator findByPrincipal(final UserAccount principal) {
		return this.findByPrincipal(principal.getId());
	}

	public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {
		final Administrator result;
		result = this.create();

		final UserAccount account = adminForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(adminForm.getAddress());
		result.setEmail(adminForm.getEmail());
		result.setName(adminForm.getName());
		//result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), adminForm.getPhoneNumber()));
		result.setPhoneNumber(adminForm.getPhoneNumber());
		result.setPhoto(adminForm.getPhoto());
		final String surnames[] = adminForm.getSurnames().split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		result.setSurnames(surNames);

		result.setCreditCard(adminForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public void flush() {
		this.adminRepository.flush();
	}

}
