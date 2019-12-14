
package acme.features.authenticated.creditcard;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.creditcard.Creditcard;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedCreditCardUpdateService implements AbstractUpdateService<Authenticated, Creditcard> {

	// Internal state -------------------------------------------------------------------------------------

	@Autowired
	private AuthenticatedCreditCardRepository repository;


	//AbstractUpdateService<Authenticated, Creditcard> interface ------------------------------------------
	@Override
	public boolean authorise(final Request<Creditcard> request) {
		assert request != null;
		Creditcard creditCart;
		Principal principal;
		Integer id;
		boolean res;

		principal = request.getPrincipal();

		id = request.getModel().getInteger("id");

		creditCart = this.repository.findOneCreditCardBySponsorId(id);

		res = principal.getAccountId() == creditCart.getSponsor().getUserAccount().getId();

		return res;
	}

	@Override
	public void bind(final Request<Creditcard> request, final Creditcard entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Creditcard> request, final Creditcard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creditNumber", "name", "surname", "expiration", "securityCode", "sponsorId");

	}

	@Override
	public Creditcard findOne(final Request<Creditcard> request) {
		assert request != null;

		Creditcard result;
		Integer id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneCreditCardBySponsorId(id);

		return result;
	}

	@Override
	public void validate(final Request<Creditcard> request, final Creditcard entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean hasExpiration, isFuture, hasNumber, hasNameOwner, hasSurname, hasSecurityCode, securityCodePattern;
		Date now;

		now = new Date(System.currentTimeMillis() - 1);

		// Expiration validation ------------------------------------------------------------------------------------
		hasExpiration = entity.getExpiration() != null;
		errors.state(request, hasExpiration, "expiration", "authenticated.sponsor.error.must-have-expiration");
		if (hasExpiration) {
			isFuture = now.before(entity.getExpiration());
			errors.state(request, isFuture, "expiration", "authenticated.sponsor.error.expirated");
		}

		// Number validation ----------------------------------------------------------------------------------------

		hasNumber = entity.getCreditNumber() != null && !entity.getCreditNumber().isEmpty();
		errors.state(request, hasNumber, "creditNumber", "authenticated.sponsor.error.must-have-creditNumber");

		// Name validation ------------------------------------------------------------------------------------------

		hasNameOwner = entity.getName() != null && !entity.getName().isEmpty();
		errors.state(request, hasNameOwner, "name", "authenticated.sponsor.error.must-have-name");

		// Surname validation ---------------------------------------------------------------------------------------

		hasSurname = entity.getSurname() != null && !entity.getSurname().isEmpty();
		errors.state(request, hasSurname, "surname", "authenticated.sponsor.error.must-have-surname");

		// Security code validation ----------------------------------------------------------------------------------

		hasSecurityCode = entity.getSecurityCode() != null && !entity.getSecurityCode().isEmpty();
		errors.state(request, hasSecurityCode, "securityCode", "authenticated.sponsor.error.must-have-securityCode");
		if (hasSecurityCode) {
			securityCodePattern = entity.getSecurityCode().matches("^[0-9]{3}$");
			errors.state(request, securityCodePattern, "securityCode", "authenticated.sponsor.error.must-have-securityCode-pattern");
		}

	}

	@Override
	public void update(final Request<Creditcard> request, final Creditcard entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

	@Override
	public void onSuccess(final Request<Creditcard> request, final Response<Creditcard> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
