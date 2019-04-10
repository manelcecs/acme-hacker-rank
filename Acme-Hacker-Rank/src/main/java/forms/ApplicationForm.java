
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Curricula;
import domain.Position;

public class ApplicationForm {

	private Curricula	curricula;
	private Position	position;


	@Valid
	@NotNull
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Valid
	@NotNull
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
