
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class SearchForm extends DomainEntity {

	//Atributes
	private String	keyword;


	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
}
