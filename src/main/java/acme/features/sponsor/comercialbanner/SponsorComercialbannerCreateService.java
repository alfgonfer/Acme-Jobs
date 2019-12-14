
package acme.features.sponsor.comercialbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.comercialbanner.Comercialbanner;
import acme.entities.configuration.Configuration;
import acme.entities.creditcard.Creditcard;
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

		request.unbind(entity, model, "urlPicture", "slogan", "urlTarget", "finalMode");

	}

	@Override
	public Comercialbanner instantiate(final Request<Comercialbanner> request) {
		assert request != null;

		Sponsor sponsor;
		Creditcard creditCard;
		Principal principal;
		Comercialbanner result;
		int principalId;

		principal = request.getPrincipal();
		principalId = principal.getAccountId();

		sponsor = this.repository.findOneSponsorByUserAccountId(principalId);

		creditCard = this.repository.findOneCreditCardBySponsorId(sponsor.getId());
		result = new Comercialbanner();
		result.setCreditCard(creditCard.getCreditNumber());
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

		boolean hasSlogan, hasSpamSlogan;

		hasSlogan = entity.getSlogan() != null && !entity.getSlogan().isEmpty();
		errors.state(request, hasSlogan, "slogan", "sponsor.comercialbanner.error.must-have-slogan");

		if (hasSlogan) {

			configuration = this.ConfigurationRepository.findConfiguration();
			spamWords = configuration.getSpamWords();
			spamThreshold = configuration.getSpamThreshold();

			hasSpamSlogan = Spamfilter.spamThreshold(entity.getSlogan(), spamWords, spamThreshold);
			errors.state(request, !hasSpamSlogan, "slogan", "sponsor.comercialbanner.error.must-have-not-spam-slogan");
		}

	}

	@Override
	public void create(final Request<Comercialbanner> request, final Comercialbanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
