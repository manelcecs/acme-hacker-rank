
package services;

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

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.ValidatorCollection;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private UserAccountRepository	accountRepository;
	@Autowired
	private AdministratorRepository	adminRepository;
	@Autowired
	private AdminConfigService		adminConfigService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private Validator				validator;


	public Administrator create() {
		final Administrator res = new Administrator();
		res.setSpammer(false);
		res.setBanned(false);
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());
		return res;
	}

	public Administrator save(final Administrator admin) {

		Assert.isTrue(admin != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
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

	public Administrator findOne(final int actorId) {
		return this.adminRepository.findOne(actorId);
	}

	public Administrator findByPrincipal(final UserAccount principal) {
		return this.adminRepository.findByPrincipal(principal.getId());
	}

	public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {

		if (!this.validateEmail(adminForm.getEmail()))
			binding.rejectValue("email", "administrator.edit.email.error");
		if (!adminForm.getUserAccount().getPassword().equals(adminForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "administrator.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(adminForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "administrator.edit.userAccount.username.error");
		if (!adminForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "administrator.edit.termsAndConditions.error");
		if (adminForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "administrator.edit.surnames.error");

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
		result.setVatNumber(adminForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), adminForm.getPhoneNumber()));
		result.setPhoneNumber(adminForm.getPhoneNumber());
		result.setPhoto(adminForm.getPhoto());

		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(adminForm.getSurnames()));

		result.setCreditCard(adminForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {

		if (!this.validateEmail(admin.getEmail()))
			binding.rejectValue("email", "administrator.edit.email.error");
		if (admin.getSurnames().isEmpty())
			binding.rejectValue("surnames", "administrator.edit.surnames.error");

		final Administrator result;
		result = this.findByPrincipal(LoginService.getPrincipal());
		result.setAddress(admin.getAddress());
		result.setEmail(admin.getEmail());
		result.setName(admin.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), admin.getPhoneNumber()));
		result.setPhoto(admin.getPhoto());
		result.setVatNumber(admin.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(admin.getSurnames()));
		result.setCreditCard(admin.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public void flush() {
		this.adminRepository.flush();
	}

	//DASHBOARD-------------------------------------------------------------

	public Double getAvgOfPositionsPerCompany() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfPositionsPerCompany();
	}

	public Integer getMinimumOfPositionsPerCompany() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfPositionsPerCompany();
	}

	public Integer getMaximumOfPositionsPerCompany() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfPositionsPerCompany();
	}

	public Double getSDOfPositionsPerCompany() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfPositionsPerCompany();
	}

	public Double getAvgOfApplicationsPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfApplicationsPerHacker();
	}

	public Integer getMinimumOfApplicationsPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfApplicationsPerHacker();
	}

	public Integer getMaximumOfApplicationsPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfApplicationsPerHacker();
	}

	public Double getSDOfApplicationsPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfApplicationsPerHacker();
	}

	public Double getAvgOfSalariesOffered() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfSalariesOffered();
	}

	public Integer getMinimumOfSalariesOffered() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfSalariesOffered();
	}

	public Integer getMaximumOfSalariesOffered() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfSalariesOffered();
	}

	public Double getSDOfSalariesOffered() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfSalariesOffered();
	}

	public Double getAvgOfCurriculaPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfCurriculaPerHacker();
	}

	public Integer getMinimumOfCurriculaPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfCurriculaPerHacker();
	}

	public Integer getMaximumOfCurriculaPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfCurriculaPerHacker();
	}

	public Double getSDOfCurriculaPerHacker() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfCurriculaPerHacker();
	}

	public Double getRatioOfEmptyVsNotEmptyFinders() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getRatioOfEmptyVsNotEmptyFinders();
	}

	public Double getAvgOfResultsInFinders() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfResultsInFinders();
	}

	public Integer getMinimumOfResultsInFinders() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfResultsInFinders();
	}

	public Integer getMaximumOfResultsInFinders() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfResultsInFinders();
	}

	public Double getSDOfResultsInFinders() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfResultsInFinders();
	}

	public Boolean validateEmail(final String email) {

		Boolean valid = false;

		final Pattern emailPattern = Pattern.compile("^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[A-Za-z0-9]{1,}[@]{1}[>]{1}|[A-Za-z0-9]{1,}[@]{1})$");

		final Matcher mEmail = emailPattern.matcher(email.toLowerCase());
		if (mEmail.matches())
			valid = true;
		return valid;
	}

	public Administrator getOne() {
		return this.adminRepository.findAll().get(0);
	}

}
