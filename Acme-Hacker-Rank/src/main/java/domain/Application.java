
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;
	private Date		momentSubmit;
	private String		status;

	private Curricula	curricula;
	private Answer		answer;


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getMomentSubmit() {
		return this.momentSubmit;
	}

	public void setMomentSubmit(final Date momentSubmit) {
		this.momentSubmit = momentSubmit;
	}

	@NotBlank
	@SafeHtml
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	@OneToOne(optional = false)
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurriculum(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Valid
	@OneToOne(optional = true, cascade = CascadeType.REMOVE)
	public Answer getAnswer() {
		return this.answer;
	}

	public void setAnswer(final Answer answer) {
		this.answer = answer;
	}

}
