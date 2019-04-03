
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class CompanyForm extends ActorForm {

	private String	email;
	private String	companyName;


	@NotBlank
	@SafeHtml
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@NotBlank
	@Pattern(regexp = "^([A-Za-z0-9_.]{1,}[@]{1}[a-z]{1,}[.]{1}[a-z]{1,})|([A-Za-z0-9_.]{1,}[<]{1}[A-Za-z0-9]{1,}[@]{1}[a-z]{2,}[.]{1}[a-z]{2,}[>]{1})|([A-Za-z0-9._]{1,}[<]{1}[A-Za-z0-9]{1,}[@]{1}[>]{1})|([A-Za-z0-9._]{1,}[@]{1})$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
