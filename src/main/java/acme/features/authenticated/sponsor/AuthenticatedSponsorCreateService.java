
package acme.features.authenticated.sponsor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.creditcard.Creditcard;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedSponsorCreateService implements AbstractCreateService<Authenticated, Sponsor> {

	// Internal state -------------------------------------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;


	// AbstractCreateService<Authenticated, Sponsor> interface --------------------------------------------

	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "creditNumber", "name", "surname", "expiration", "securityCode");

	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "orgName");
	}

	@Override
	public Sponsor instantiate(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Sponsor();

		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		boolean hasExpiration, isFuture, hasNumber, hasNameOwner, hasSurname, hasSecurityCode, securityCodePattern;
		Date now;

		now = new Date(System.currentTimeMillis() - 1);

		// Expiration validation ------------------------------------------------------------------------------------
		hasExpiration = request.getModel().getDate("expiration") != null;
		errors.state(request, hasExpiration, "expiration", "authenticated.sponsor.error.must-have-expiration");
		if (hasExpiration) {
			isFuture = now.before(request.getModel().getDate("expiration"));
			errors.state(request, isFuture, "expiration", "authenticated.sponsor.error.expirated");
		}

		// Number validation ----------------------------------------------------------------------------------------

		hasNumber = request.getModel().getString("creditNumber") != null && !request.getModel().getString("creditNumber").isEmpty();
		errors.state(request, hasNumber, "creditNumber", "authenticated.sponsor.error.must-have-creditNumber");

		// Name validation ------------------------------------------------------------------------------------------

		hasNameOwner = request.getModel().getString("name") != null && !request.getModel().getString("name").isEmpty();
		errors.state(request, hasNameOwner, "name", "authenticated.sponsor.error.must-have-name");

		// Surname validation ---------------------------------------------------------------------------------------

		hasSurname = request.getModel().getString("surname") != null && !request.getModel().getString("surname").isEmpty();
		errors.state(request, hasSurname, "surname", "authenticated.sponsor.error.must-have-surname");

		// Security code validation ----------------------------------------------------------------------------------

		hasSecurityCode = request.getModel().getString("securityCode") != null && !request.getModel().getString("securityCode").isEmpty();
		errors.state(request, hasSecurityCode, "securityCode", "authenticated.sponsor.error.must-have-securityCode");
		if (hasSecurityCode) {
			securityCodePattern = request.getModel().getString("securityCode").toString().matches("^[0-9]{3}$");
			errors.state(request, securityCodePattern, "securityCode", "authenticated.sponsor.error.must-have-securityCode-pattern");
		}

	}

	@Override
	public void create(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		Creditcard creditCard = new Creditcard();

		String number = request.getModel().getString("creditNumber");

		creditCard.setCreditNumber(number.trim());
		creditCard.setName(request.getModel().getString("name"));
		creditCard.setSurname(request.getModel().getString("surname"));
		creditCard.setExpiration(request.getModel().getDate("expiration"));
		creditCard.setSecurityCode(request.getModel().getString("securityCode"));
		creditCard.setSponsor(entity);

		this.repository.save(entity);
		this.repository.save(creditCard);
	}

	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
