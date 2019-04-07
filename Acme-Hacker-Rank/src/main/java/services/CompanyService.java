
package services;

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

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;
import domain.Company;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository	companyRepository;
	@Autowired
	private CompanyRepository		companyRepository;
	//@Autowired
	//private AdminConfigRepository	companyConfigRepository;
	@Autowired
	private Validator				validator;


	public Company findByPrincipal(final int idPrincipal) {
		return this.companyRepository.findByPrincipal(idPrincipal);

		//TODO: a�adir cajas de mensajes

		return res;
	}

	public Company save(final Company company) {
		Assert.isTrue(company != null);
		Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());

		if (company.getId() == 0) {
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

	public Company findByPrincipal(final int principalId) {
		return this.companyRepository.findByPrincipal(principalId);
	}
	public Company findByPrincipal(final UserAccount principal) {
		return this.findByPrincipal(principal.getId());
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
		result = this.create();

		final UserAccount account = companyForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(companyForm.getAddress());
		result.setEmail(companyForm.getEmail());
		result.setCompanyName(companyForm.getCompanyName());
		result.setName(companyForm.getName());
		//TODO:
		//result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.companyConfigService.getAdminConfig().getCountryCode(), companyForm.getPhoneNumber()));
		result.setPhoneNumber(companyForm.getPhoneNumber());
		result.setPhoto(companyForm.getPhoto());
		final String surnames[] = companyForm.getSurnames().split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		result.setSurnames(surNames);

		result.setCreditCard(companyForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

}
