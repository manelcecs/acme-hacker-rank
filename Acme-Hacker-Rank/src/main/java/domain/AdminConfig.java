
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

@Entity
@Access(AccessType.PROPERTY)
public class AdminConfig extends DomainEntity {

	private int					cacheFinder;
	private int					resultsFinder;
	private Collection<String>	spamWords;
	private String				systemName;
	private String				welcomeMessageEN;
	private String				welcomeMessageES;
	private Integer				countryCode;
	private String				bannerURL;


	@Range(min = 1, max = 24)
	@NotNull
	public int getCacheFinder() {
		return this.cacheFinder;
	}

	public void setCacheFinder(final int cacheFinder) {
		this.cacheFinder = cacheFinder;
	}

	@Range(min = 1, max = 100)
	@NotNull
	public int getResultsFinder() {
		return this.resultsFinder;
	}

	public void setResultsFinder(final int resultsFinder) {
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
	@Pattern(regexp = "^(\\+[1-9]|\\+[1-9][1-9]|\\+[1-9][1-9][1-9])$")
	public Integer getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final Integer countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

}
