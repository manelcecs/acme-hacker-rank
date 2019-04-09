
package services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;
import domain.Hacker;
import domain.Message;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private AdministratorService	administratorService;


	public Actor save(final Actor actor) {
		return this.actorRepository.save(actor);
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int idActor) {
		return this.actorRepository.findOne(idActor);
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.getByUserAccount(userAccount.getId());
	}

	public Actor getByMessageBox(final int idBox) {
		return this.actorRepository.getByMessageBox(idBox);
	}

	public Collection<Actor> findEliminatedActors() {
		return this.actorRepository.findEliminatedActors();
	}

	// Workaround for the problem of hibernate with inheritances
	public Actor getActor(final int idActor) {
		return this.actorRepository.getActor(idActor);
	}

	public void updateSpam() {
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");
		for (final Actor a : this.actorRepository.findAll()) {
			final Collection<Message> allMessages = this.messageService.findAllByActor(a.getId());
			if (allMessages.size() > 0) {
				final Integer totalMessages = allMessages.size();
				final Integer spamMessages = this.messageService.getSpamMessages(allMessages);
				final boolean spammer = spamMessages >= 0.1 * totalMessages;
				if (a.getSpammer() != null) {
					if (a.getSpammer() != spammer) {
						a.setSpammer(spammer);
						this.actorRepository.save(a);
					}
				} else {
					a.setSpammer(spammer);
					this.actorRepository.save(a);
				}
			}
		}
	}

	public void ban(final Actor actor) {
		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(!(actor.equals(this.findByUserAccount(principal))));
		Assert.isTrue(actor.getSpammer());

		actor.setBanned(true);

		final UserAccount userAccount = actor.getUserAccount();
		userAccount.removeAuthority(userAccount.getAuthorities().iterator().next());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.BAN);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);

		final UserAccount res = this.userAccountRepository.save(userAccount);
		actor.setUserAccount(res);

		this.save(actor);
	}

	public void unban(final Actor actor) {
		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(!(actor.equals(this.findByUserAccount(principal))));

		actor.setBanned(false);

		final UserAccount userAccount = actor.getUserAccount();
		userAccount.removeAuthority(userAccount.getAuthorities().iterator().next());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();

		if (this.hackerService.findOne(actor.getId()) != null) {
			authority.setAuthority(Authority.HACKER);
			authorities.add(authority);
		} else if (this.companyService.findOne(actor.getId()) != null) {
			authority.setAuthority(Authority.COMPANY);
			authorities.add(authority);
		} else if (this.administratorService.findOne(actor.getId()) != null) {
			authority.setAuthority(Authority.ADMINISTRATOR);
			authorities.add(authority);
		}

		userAccount.setAuthorities(authorities);
		final UserAccount res = this.userAccountRepository.save(userAccount);
		actor.setUserAccount(res);

		this.save(actor);
	}

	public Collection<Actor> getSpammerActors() {
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");
		return this.actorRepository.findSpamActors();
	}

	//:TODO todos los datos
	public String exportData() throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		List<Message> messages;

		String json = "";

		final UserAccount principal = LoginService.getPrincipal();
		String authority = AuthorityMethods.getLoggedAuthority().getAuthority();

		if (authority.equals("BAN"))
			authority = this.checkAuthorityIsBanned(principal);

		switch (authority) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(administrator.getId());
			json = json + mapper.writeValueAsString(administrator);
			json = json + mapper.writeValueAsString(messages);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(hacker.getId());
			json = json + mapper.writeValueAsString(hacker);
			json = json + mapper.writeValueAsString(messages);
			break;

		case "COMPANY":
			final Company company = this.companyService.findByPrincipal(principal);
			messages = (List<Message>) this.messageService.findAllByActor(company.getId());

			json = json + mapper.writeValueAsString(company);
			json = json + mapper.writeValueAsString(messages);

			break;
		}
		return json;
	}

	//:TODO todos los datos
	public void deleteData() throws ParseException {

		final UserAccount principal = LoginService.getPrincipal();
		String authority = AuthorityMethods.getLoggedAuthority().getAuthority();

		if (authority.equals("BAN"))
			authority = this.checkAuthorityIsBanned(principal);

		switch (authority) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findByPrincipal(principal);
			final UserAccount userAdmin = administrator.getUserAccount();
			userAdmin.setAuthorities(null);
			userAdmin.setPassword(this.generatePassword());
			administrator.setUserAccount(userAdmin);
			administrator.setAddress("---");
			administrator.setBanned(false);
			final CreditCard creditCardAdmin = administrator.getCreditCard();
			creditCardAdmin.setCvv(999);
			creditCardAdmin.setExpirationMonth(1);
			creditCardAdmin.setExpirationYear(0);
			creditCardAdmin.setHolder("---");
			creditCardAdmin.setMake("---");
			creditCardAdmin.setNumber("4636711719209732");
			administrator.setCreditCard(creditCardAdmin);
			administrator.setVatNumber("----");
			administrator.setEmail("---");
			administrator.setName("anonymous");
			administrator.setPhoneNumber("----");

			//:TODO Curriculums

			this.administratorService.save(administrator);
			break;

		case "HACKER":
			final Hacker hacker = this.hackerService.findByPrincipal(principal);
			hacker.setAddress("---");
			hacker.setBanned(false);
			hacker.setEmail("---");
			hacker.setName("anonymous");
			hacker.setPhoneNumber("----");
			final CreditCard creditCardhacker = hacker.getCreditCard();
			creditCardhacker.setCvv(999);
			creditCardhacker.setExpirationMonth(1);
			creditCardhacker.setExpirationYear(0);
			creditCardhacker.setHolder("---");
			creditCardhacker.setMake("---");
			creditCardhacker.setNumber("4636711719209732");
			hacker.setCreditCard(creditCardhacker);
			hacker.setVatNumber("----");
			final UserAccount userHacker = hacker.getUserAccount();
			userHacker.setAuthorities(null);
			userHacker.setPassword(this.generatePassword());
			hacker.setUserAccount(userHacker);

			//:TODO Curriculums

			this.hackerService.save(hacker);
			break;

		case "COMPANY":
			final Company company = this.companyService.findByPrincipal(principal);
			final UserAccount userCompany = company.getUserAccount();
			userCompany.setAuthorities(null);
			userCompany.setPassword(this.generatePassword());
			final CreditCard creditCardCompany = company.getCreditCard();
			creditCardCompany.setCvv(999);
			creditCardCompany.setExpirationMonth(1);
			creditCardCompany.setExpirationYear(0);
			creditCardCompany.setHolder("---");
			creditCardCompany.setMake("---");
			creditCardCompany.setNumber("4636711719209732");
			company.setCreditCard(creditCardCompany);
			company.setVatNumber("----");
			company.setUserAccount(userCompany);
			company.setCompanyName("Anonymous");
			company.setAddress("---");
			company.setBanned(false);
			company.setEmail("---");

			//:TODO Curriculums

			this.companyService.save(company);
			break;

		}
	}

	//Utiles
	private String generatePassword() {
		final SecureRandom random = new SecureRandom();
		final String text = new BigInteger(130, random).toString(32);
		return text;
	}

	public String checkAuthorityIsBanned(final UserAccount userAccount) {
		String res = "";
		final Actor actor = this.findByUserAccount(userAccount);

		if (this.hackerService.findOne(actor.getId()) != null)
			res = "HACKER";
		else if (this.companyService.findOne(actor.getId()) != null)
			res = "COMPANY";
		else if (this.administratorService.findOne(actor.getId()) != null)
			res = "ADMINISTRATOR";

		return res;
	}

}
