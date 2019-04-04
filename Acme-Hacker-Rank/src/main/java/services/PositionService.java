
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.Position;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	@Autowired
	TickerService		tickerService;

	@Autowired
	CompanyService		companyService;

	@Autowired
	PositionRepository	positionRepository;

	@Autowired
	Validator			validator;


	public Position findOne(final int idPosition) {
		return this.positionRepository.findOne(idPosition);
	}

	public Position reconstruct(final PositionForm positionForm, final BindingResult binding) {
		final Company company = this.companyService.findByPrincipal(LoginService.getPrincipal().getId());

		Position position;
		if (positionForm.getId() == 0) {
			position = new Position();
			position.setTicker(this.tickerService.generateTicker(company.getCompanyName()));
			position.setCancelled(false);
			position.setDraft(true);
			position.setCompany(company);
		} else
			position = this.positionRepository.findOne(positionForm.getId());

		position.setTitle(positionForm.getTitle());
		position.setDescription(positionForm.getDescription());
		position.setDeadline(positionForm.getDeadline());
		position.setProfileRequired(positionForm.getProfileRequired());
		position.setSkillsRequired(positionForm.getSkillsRequired());
		position.setTechnologiesRequired(positionForm.getTechnologiesRequired());
		position.setSalaryOffered(positionForm.getSalaryOffered());

		this.validator.validate(position, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return position;
	}

	public PositionForm castToForm(final Position position) {
		final PositionForm positionForm = new PositionForm();

		positionForm.setId(position.getId());
		positionForm.setVersion(position.getVersion());

		positionForm.setTitle(position.getTitle());
		positionForm.setDescription(position.getDescription());
		positionForm.setDeadline(position.getDeadline());
		positionForm.setProfileRequired(position.getProfileRequired());
		positionForm.setSkillsRequired(position.getSkillsRequired());
		positionForm.setTechnologiesRequired(position.getTechnologiesRequired());
		positionForm.setSalaryOffered(position.getSalaryOffered());

		return positionForm;
	}

	public Position save(final Position position) {
		final UserAccount principal = LoginService.getPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority("COMPANY");
		Assert.isTrue(principal.getAuthorities().contains(authority));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		return this.positionRepository.save(position);
	}

	public Collection<Position> getPositionsOfCompany(final int idCompany) {
		return this.positionRepository.getPositionsOfCompany(idCompany);
	}

	public Position changeDraft(final Position position) {

		final UserAccount principal = LoginService.getPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority("COMPANY");
		Assert.isTrue(principal.getAuthorities().contains(authority));

		final Company company = this.companyService.findByPrincipal(principal.getId());

		Assert.isTrue(position.getCompany().getId() == company.getId());

		position.setDraft(false);

		return this.positionRepository.save(position);

	}

}
