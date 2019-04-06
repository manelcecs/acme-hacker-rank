
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;
import domain.Actor;
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
				final Integer spamMessages = this.messageService.getSpamMessagges(allMessages);
				final boolean spammer = spamMessages > 0.1 * totalMessages;
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
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");

		if (!(actor.equals(this.findByUserAccount(principal)))) {
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
		}

		this.save(actor);
	}

	public void unban(final Actor actor) {
		final UserAccount principal = LoginService.getPrincipal();
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");

		if (!(actor.equals(this.findByUserAccount(principal)))) {
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
		}
		this.save(actor);
	}

	public Collection<Actor> getSpammerActors() {
		AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR");
		return this.actorRepository.findSpamActors();
	}
}
