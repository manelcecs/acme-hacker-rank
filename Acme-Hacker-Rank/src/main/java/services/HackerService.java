
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
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
	@Autowired
	private AdminConfigService		adminConfigService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private Validator				validator;


	public Hacker create() {
		final Hacker res = new Hacker();
		res.setSpammer(false);
		res.setBanned(false);
		//TODO: aï¿½adir finder
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());
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

	public Hacker findByPrincipal(final int principalId) {
		return this.hackerRepository.findByPrincipal(principalId);
	}
	public Hacker findByPrincipal(final UserAccount principal) {
		return this.findByPrincipal(principal.getId());
	}

	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) {

		if (!this.validateEmail(hackerForm.getEmail()))
			binding.rejectValue("email", "hacker.edit.email.error");
		if (!hackerForm.getUserAccount().getPassword().equals(hackerForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "hacker.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(hackerForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "hacker.edit.userAccount.username.error");
		if (!hackerForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "hacker.edit.termsAndConditions.error");
		if (hackerForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "hacker.edit.surnames.error");

		final Hacker result;
		result = this.create();

		final UserAccount account = hackerForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(hackerForm.getAddress());
		result.setEmail(hackerForm.getEmail());
		result.setName(hackerForm.getName());
		result.setVatNumber(hackerForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), hackerForm.getPhoneNumber()));
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setPhoto(hackerForm.getPhoto());
		//TODO:añadir los inputs de varios surnames
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

	public Hacker reconstruct(final Hacker hacker, final BindingResult binding) {
		final Hacker result;
		result = this.findByPrincipal(LoginService.getPrincipal().getId());
		result.setAddress(hacker.getAddress());
		result.setEmail(hacker.getEmail());
		result.setName(hacker.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), hacker.getPhoneNumber()));
		result.setPhoto(hacker.getPhoto());
		result.setVatNumber(hacker.getVatNumber());
		result.setSurnames(hacker.getSurnames());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Boolean validateEmail(final String email) {

		Boolean valid = false;

		final Pattern emailPattern = Pattern.compile("^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,}[>]{1}|[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,})$");

		final Matcher mEmail = emailPattern.matcher(email.toLowerCase());
		if (mEmail.matches())
			valid = true;
		return valid;
	}

}
