
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class AdminConfigForm {

	private Integer	cacheFinder;
	private Integer	resultsFinder;
	private String	spamWord;
	private String	systemName;
	private String	welcomeMessageEN;
	private String	welcomeMessageES;
	private String	countryCode;
	private String	bannerURL;


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

	public String getSpamWord() {
		return this.spamWord;
	}

	public void setSpamWord(final String spamWord) {
		this.spamWord = spamWord;
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
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
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
