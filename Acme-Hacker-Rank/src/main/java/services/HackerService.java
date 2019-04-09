
package services;

import java.text.ParseException;
import java.util.Collection;
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
import utiles.ValidatorCollection;
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
	private FinderService			finderService;

	@Autowired
	private Validator				validator;


	public Hacker create() throws ParseException {
		final Hacker res = new Hacker();
		res.setSpammer(false);
		res.setBanned(false);
		res.setFinder(this.finderService.generateNewFinder());
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());
		return res;
	}
	public Hacker save(final Hacker hacker) {
		Assert.isTrue(hacker != null);

		if (hacker.getId() == 0) {
			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
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

	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) throws ParseException {

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
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(hackerForm.getSurnames()));

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

	public Hacker reconstruct(final Hacker hacker, final BindingResult binding) {

		if (!this.validateEmail(hacker.getEmail()))
			binding.rejectValue("email", "hacker.edit.email.error");
		if (hacker.getSurnames().isEmpty())
			binding.rejectValue("surnames", "hacker.edit.surnames.error");

		final Hacker result;
		result = this.findByPrincipal(LoginService.getPrincipal());
		result.setAddress(hacker.getAddress());
		result.setEmail(hacker.getEmail());
		result.setName(hacker.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), hacker.getPhoneNumber()));
		result.setPhoto(hacker.getPhoto());
		result.setVatNumber(hacker.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(hacker.getSurnames()));

		this.validator.validate(result, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			throw new ValidationException();
		}
		return result;
	}

	public Boolean validateEmail(final String email) {

		Boolean valid = false;

		final Pattern emailPattern = Pattern
			.compile("^([A-Za-z0-9_.]{1,}[@]{1}[a-z]{1,}[.]{1}[a-z]{1,})|([A-Za-z0-9_.]{1,}[<]{1}[A-Za-z0-9]{1,}[@]{1}[a-z]{2,}[.]{1}[a-z]{2,}[>]{1})|([A-Za-z0-9._]{1,}[<]{1}[A-Za-z0-9]{1,}[@]{1}[>]{1})|([A-Za-z0-9._]{1,}[@]{1})$");

		final Matcher mEmail = emailPattern.matcher(email.toLowerCase());
		if (mEmail.matches())
			valid = true;
		return valid;
	}

}
