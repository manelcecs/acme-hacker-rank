
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

public class PositionForm extends DomainEntity {

	//Atributes
	private String				title;
	private String				description;
	private Date				deadline;
	private String				profileRequired;
	private Collection<String>	skillsRequired;
	private Collection<String>	technologiesRequired;
	private double				salaryOffered;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@NotNull
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@SafeHtml
	@NotBlank
	public String getProfileRequired() {
		return this.profileRequired;
	}

	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}

	@ElementCollection
	public Collection<String> getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final Collection<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@ElementCollection
	public Collection<String> getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final Collection<String> technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	@Min(0)
	public double getSalaryOffered() {
		return this.salaryOffered;
	}

	public void setSalaryOffered(final double salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

}
