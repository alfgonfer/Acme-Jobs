
package acme.features.sponsor.comercialbanner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.comercialbanner.Comercialbanner;
import acme.entities.configuration.Configuration;
import acme.entities.roles.Sponsor;
import acme.features.utiles.ConfigurationRepository;
import acme.features.utiles.Spamfilter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class SponsorComercialbannerCreateService implements AbstractCreateService<Sponsor, Comercialbanner> {

	// Internal state ------------------------------------------------------------------------------------------------

	@Autowired
	private SponsorComercialbannerRepository	repository;

	@Autowired
	private ConfigurationRepository				ConfigurationRepository;


	// AbstractCreateService<Sponsor, Comercialbanner> interface ------------------------------------------------------

	@Override
	public boolean authorise(final Request<Comercialbanner> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Comercialbanner> request, final Comercialbanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Comercialbanner> request, final Comercialbanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "urlPicture", "slogan", "urlTarget", "finalMode", "creditNumber", "name", "surname", "expiration", "securityCode", "type");

	}

	@Override
	public Comercialbanner instantiate(final Request<Comercialbanner> request) {
		assert request != null;

		Sponsor sponsor;
		Principal principal;
		Comercialbanner result;
		int principalId;

		principal = request.getPrincipal();
		principalId = principal.getAccountId();

		sponsor = this.repository.findOneSponsorByUserAccountId(principalId);

		result = new Comercialbanner();
		result.setFinalMode(false);
		result.setSponsor(sponsor);
		return result;
	}

	@Override
	public void validate(final Request<Comercialbanner> request, final Comercialbanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Configuration configuration;
		String spamWords;
		Double spamThreshold;

		boolean hasSlogan, hasSpamSlogan, hasExpiration;
		boolean isFuture, hasNumber, hasNameOwner, hasSurname, hasSecurityCode, securityCodePattern;
		Date now;
		now = new Date(System.currentTimeMillis() - 1);

		// Slogan validation ---------------------------------------------------------------------------------
		hasSlogan = entity.getSlogan() != null && !entity.getSlogan().isEmpty();
		errors.state(request, hasSlogan, "slogan", "sponsor.comercialbanner.error.must-have-slogan");

		if (hasSlogan) {

			configuration = this.ConfigurationRepository.findConfiguration();
			spamWords = configuration.getSpamWords();
			spamThreshold = configuration.getSpamThreshold();

			hasSpamSlogan = Spamfilter.spamThreshold(entity.getSlogan(), spamWords, spamThreshold);
			errors.state(request, !hasSpamSlogan, "slogan", "sponsor.comercialbanner.error.must-have-not-spam-slogan");
		}

		// Expiration validation ------------------------------------------------------------------------------------
		hasExpiration = entity.getExpiration() != null;
		errors.state(request, hasExpiration, "expiration", "authenticated.sponsor.error.must-have-expiration");
		if (hasExpiration) {
			isFuture = now.before(entity.getExpiration());
			errors.state(request, isFuture, "expiration", "authenticated.sponsor.error.expirated");
		}

		// Number validation ----------------------------------------------------------------------------------------

		hasNumber = entity.getCreditNumber() != null;
		errors.state(request, hasNumber, "creditNumber", "authenticated.sponsor.error.must-have-creditNumber");

		// Name validation ------------------------------------------------------------------------------------------

		hasNameOwner = entity.getName() != null;
		errors.state(request, hasNameOwner, "name", "authenticated.sponsor.error.must-have-name");

		// Surname validation ---------------------------------------------------------------------------------------

		hasSurname = entity.getSurname() != null;
		errors.state(request, hasSurname, "surname", "authenticated.sponsor.error.must-have-surname");

		// Security code validation ----------------------------------------------------------------------------------

		hasSecurityCode = entity.getSecurityCode() != null;
		errors.state(request, hasSecurityCode, "securityCode", "authenticated.sponsor.error.must-have-securityCode");

	}

	@Override
	public void create(final Request<Comercialbanner> request, final Comercialbanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
