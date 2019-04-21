
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "deadline, salaryOffered"), @Index(columnList = "draft, company"), @Index(columnList = "company"), @Index(columnList = "draft, cancelled"), @Index(columnList = "draft, cancelled, company"),
	@Index(columnList = "draft, salaryOffered"), @Index(columnList = "draft, cancelled, deadline"), @Index(columnList = "draft")
})
public class Position extends DomainEntity {

	//Atributes
	private String				title;
	private String				description;
	private Date				deadline;
	private String				profileRequired;
	private Collection<String>	skillsRequired;
	private Collection<String>	technologiesRequired;
	private Double				salaryOffered;
	private boolean				draft;
	private boolean				cancelled;

	//Relationship
	private Company				company;
	private Ticker				ticker;


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

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	public Collection<String> getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final Collection<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	public Collection<String> getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final Collection<String> technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	@Min(0)
	@NotNull
	public Double getSalaryOffered() {
		return this.salaryOffered;
	}

	public void setSalaryOffered(final Double salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

	public boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}

	public boolean getCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	@Valid
	@OneToOne(optional = false)
	public Ticker getTicker() {
		return this.ticker;
	}

	public void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}

}
