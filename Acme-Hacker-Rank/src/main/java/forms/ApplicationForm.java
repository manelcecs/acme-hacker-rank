
package forms;

import javax.validation.Valid;

import domain.Curricula;
import domain.Position;

public class ApplicationForm {

	private Curricula	curricula;
	private Position	position;


	@Valid
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Valid
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
