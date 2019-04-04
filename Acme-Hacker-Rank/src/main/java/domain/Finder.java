
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Atributes
	private String					keyWord;
	private Date					deadline;
	private Date					maximumDeadLine;
	private Double					minimumSalary;

	//Relationship
	private Collection<Position>	positions;


	//Atributes getters and setters
	@SafeHtml
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getMaximumDeadLine() {
		return this.maximumDeadLine;
	}

	public void setMaximumDeadLine(final Date maximumDeadLine) {
		this.maximumDeadLine = maximumDeadLine;
	}

	@Valid
	public Double getMinimumSalary() {
		return this.minimumSalary;
	}

	public void setMinimumSalary(final Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
