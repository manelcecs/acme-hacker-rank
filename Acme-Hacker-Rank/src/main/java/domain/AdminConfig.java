
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import forms.AdminConfigForm;

@Entity
@Access(AccessType.PROPERTY)
public class AdminConfig extends DomainEntity {

	private Integer				cacheFinder;
	private Integer				resultsFinder;
	private Collection<String>	spamWords;
	private String				systemName;
	private String				welcomeMessageEN;
	private String				welcomeMessageES;
	private String				countryCode;
	private String				bannerURL;


	@Range(min = 1, max = 24)
	@NotNull
	public Integer getCacheFinder() {
		return this.cacheFinder;
	}

	public void setCacheFinder(final Integer cacheFinder) {
		this.cacheFinder = cacheFinder;
	}

	@Range(min = 1, max = 100)
	@NotNull
	public Integer getResultsFinder() {
		return this.resultsFinder;
	}

	public void setResultsFinder(final Integer resultsFinder) {
		this.resultsFinder = resultsFinder;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@SafeHtml
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}

	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^(\\+[1-9]|\\+[1-9][0-9]|\\+[1-9][0-9][0-9])$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	public AdminConfigForm castToForm() {
		final AdminConfigForm adminConfigForm = new AdminConfigForm();
		adminConfigForm.setBannerURL(this.getBannerURL());
		adminConfigForm.setCountryCode(this.getCountryCode());
		adminConfigForm.setCacheFinder(this.getCacheFinder());
		adminConfigForm.setResultsFinder(this.getResultsFinder());
		adminConfigForm.setSystemName(this.getSystemName());
		adminConfigForm.setWelcomeMessageEN(this.getWelcomeMessageEN());
		adminConfigForm.setWelcomeMessageES(this.getWelcomeMessageES());
		return adminConfigForm;

	}

}
