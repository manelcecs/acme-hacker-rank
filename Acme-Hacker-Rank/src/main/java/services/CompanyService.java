
package services;

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

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.ValidatorCollection;
import domain.Company;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private UserAccountRepository	accountRepository;
	@Autowired
	private CompanyRepository		companyRepository;
	@Autowired
	private AdminConfigService		adminConfigService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private Validator				validator;


	public Company create() {
		final Company res = new Company();
		res.setSpammer(false);
		res.setBanned(false);
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());

		return res;
	}

	public Company save(final Company company) {
		Assert.isTrue(company != null);

		if (company.getId() == 0) {
			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = company.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			company.setUserAccount(finalAccount);
		} else
			Assert.isTrue(!company.getBanned());

		final Company res = this.companyRepository.save(company);

		return res;

	}

	public void flush() {
		this.companyRepository.flush();
	}

	public Company findOne(final int companyId) {
		return this.companyRepository.findOne(companyId);
	}

	public Company findByPrincipal(final UserAccount principal) {
		return this.companyRepository.findByPrincipal(principal.getId());
	}

	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {

		if (!this.validateEmail(companyForm.getEmail()))
			binding.rejectValue("email", "company.edit.email.error");
		if (!companyForm.getUserAccount().getPassword().equals(companyForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "company.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(companyForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "company.edit.userAccount.username.error");
		if (!companyForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "company.edit.termsAndConditions.error");
		if (companyForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "company.edit.surnames.error");

		final Company result;
		result = this.create();

		final UserAccount account = companyForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.COMPANY);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(companyForm.getAddress());
		result.setEmail(companyForm.getEmail());
		result.setCompanyName(companyForm.getCompanyName());
		result.setName(companyForm.getName());
		result.setVatNumber(companyForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), companyForm.getPhoneNumber()));
		result.setPhoneNumber(companyForm.getPhoneNumber());
		result.setPhoto(companyForm.getPhoto());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(companyForm.getSurnames()));

		result.setCreditCard(companyForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			throw new ValidationException();
		}

		return result;
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	//DASHBOARD----------------------------------------------------

	public Collection<Company> getCompaniesWithMoreOffersOfPositions() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.companyRepository.getCompaniesWithMoreOffersOfPositions();
	}

	public Company reconstruct(final Company company, final BindingResult binding) {

		if (!this.validateEmail(company.getEmail()))
			binding.rejectValue("email", "company.edit.email.error");
		if (company.getSurnames().isEmpty())
			binding.rejectValue("surnames", "company.edit.surnames.error");

		final Company result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setCompanyName(company.getCompanyName());
		result.setAddress(company.getAddress());
		result.setEmail(company.getEmail());
		result.setName(company.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), company.getPhoneNumber()));
		result.setPhoto(company.getPhoto());
		result.setVatNumber(company.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(company.getSurnames()));

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
