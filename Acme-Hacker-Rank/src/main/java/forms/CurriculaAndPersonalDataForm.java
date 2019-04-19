
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class CurriculaAndPersonalDataForm {

	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	gitHubProfile;
	private String	linkedinProfile;

	private String	title;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@SafeHtml
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getGitHubProfile() {
		return this.gitHubProfile;
	}

	public void setGitHubProfile(final String gitHubProfile) {
		this.gitHubProfile = gitHubProfile;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getLinkedinProfile() {
		return this.linkedinProfile;
	}

	public void setLinkedinProfile(final String linkedinProfile) {
		this.linkedinProfile = linkedinProfile;
	}

}
