
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Atributes
	private String					keyWord;
	private Date					minimumDeadLine;
	private Date					maximumDeadLine;
	private Double					minimumSalary;
	private Date					lastUpdate;

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
	public Date getMinimumDeadLine() {
		return this.minimumDeadLine;
	}

	public void setMinimumDeadLine(final Date minimumDeadLine) {
		this.minimumDeadLine = minimumDeadLine;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getMaximumDeadLine() {
		return this.maximumDeadLine;
	}

	public void setMaximumDeadLine(final Date maximumDeadLine) {
		this.maximumDeadLine = maximumDeadLine;
	}

	@Min(0)
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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@NotNull
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
