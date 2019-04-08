
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminConfigRepository;
import utiles.AuthorityMethods;
import domain.AdminConfig;
import forms.AdminConfigForm;

@Service
@Transactional
public class AdminConfigService {

	@Autowired
	private AdminConfigRepository	adminConfigRepository;

	@Autowired
	private Validator				validator;


	public AdminConfig getAdminConfig() {
		return this.adminConfigRepository.findAll().get(0);
	}

	public AdminConfig save(final AdminConfig adminConfig) {
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");
		return this.adminConfigRepository.save(adminConfig);
	}

	public boolean existSpamWord(final String s) {
		final String palabras[] = s.split("[.,:;()?�" + " " + "\t!�]"); //:FIXME Ojo con la codificaci�n de git. Sustituir por la exclamacion e interrogacion espa�olas
		final List<String> listaPalabras = Arrays.asList(palabras);
		boolean exist = false;
		final AdminConfig administratorConfig = this.getAdminConfig();
		final Collection<String> spamWord = administratorConfig.getSpamWords();
		for (final String palabraLista : listaPalabras)
			if (spamWord.contains(palabraLista.toLowerCase().trim())) {
				exist = true;
				break;
			}
		return exist;
	}

	public AdminConfig reconstruct(final AdminConfigForm adminConfigForm, final BindingResult binding) {
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		adminConfig.setBannerURL(adminConfigForm.getBannerURL());
		adminConfig.setCacheFinder(adminConfigForm.getCacheFinder());
		adminConfig.setCountryCode(adminConfigForm.getCountryCode());
		adminConfig.setResultsFinder(adminConfigForm.getResultsFinder());
		adminConfig.setSystemName(adminConfigForm.getSystemName());
		adminConfig.setWelcomeMessageEN(adminConfigForm.getWelcomeMessageEN());
		adminConfig.setWelcomeMessageES(adminConfigForm.getWelcomeMessageES());

		final Collection<String> spamWords = adminConfig.getSpamWords();

		if (!(adminConfigForm.getSpamWord().trim().isEmpty())) {
			if (spamWords.contains(adminConfigForm.getSpamWord().toLowerCase()))
				binding.rejectValue("spamWord", "adminConfig.error.existSpamWord");

			spamWords.add(adminConfigForm.getSpamWord().toLowerCase());
		}
		adminConfig.setSpamWords(spamWords);

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return adminConfig;
	}

	public void deleteSpamWord(final String spamWord) {
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");

		final AdminConfig adminConfig = this.getAdminConfig();
		final Collection<String> spamWords = adminConfig.getSpamWords();
		Assert.isTrue(spamWords.contains(spamWord));
		spamWords.remove(spamWord);
		adminConfig.setSpamWords(spamWords);
		this.save(adminConfig);
	}

	public void flush() {
		this.adminConfigRepository.flush();
	}
}
