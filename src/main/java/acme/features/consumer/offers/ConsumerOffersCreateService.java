
package acme.features.consumer.offers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offers;
import acme.entities.roles.Consumer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;

@Service
public class ConsumerOffersCreateService implements AbstractCreateService<Consumer, Offers> {

	//	Internal State -------------------------------------------------------------------------------------------------------------------
	@Autowired
	ConsumerOffersRepository repository;


	// Abstract<Provider, Request> -------------------------------------------------------------------------------------------------------

	@Override
	public boolean authorise(final Request<Offers> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Offers> request, final Offers entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "accept");

	}

	@Override
	public void unbind(final Request<Offers> request, final Offers entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "description", "deadline", "ticker", "moment", "lowerRange", "majorRange");

	}

	@Override
	public Offers instantiate(final Request<Offers> request) {
		assert request != null;

		Offers result = new Offers();

		return result;
	}

	@Override
	public void validate(final Request<Offers> request, final Offers entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		boolean isDuplicated, isEuroLower, isEuroMajor, isRange, isAccepted, isFuture, hasDeadline, hasTitle, hasDescription, hasMajorRange, hasLowerRange, hasTicker;
		Date dateNow, deadline;

		dateNow = new Date(System.currentTimeMillis() - 1);

		hasDeadline = entity.getDeadline() != null;
		errors.state(request, hasDeadline, "deadline", "consumer.offers.error.must-have-deadline");

		if (hasDeadline) {
			deadline = entity.getDeadline();
			isFuture = dateNow.before(deadline);
			errors.state(request, isFuture, "deadline", "consumer.offers.error.must-be-future");

		}

		hasTicker = entity.getTicker() != null;
		errors.state(request, hasTicker, "ticker", "consumer.offers.error.tickerDuplicated", "");

		if (hasTicker) {

			isDuplicated = this.repository.findOneByTicker(entity.getTicker()) != null;
			errors.state(request, !isDuplicated, "ticker", "consumer.offers.error.tickerDuplicated");
		}

		hasMajorRange = entity.getMajorRange() != null;
		errors.state(request, hasMajorRange, "majorRange", "consumer.offers.error.not-range");

		hasLowerRange = entity.getLowerRange() != null;
		errors.state(request, hasLowerRange, "lowerRange", "consumer.offers.error.not-range");

		if (hasLowerRange && hasMajorRange) {
			Money euro = new Money();
			euro.setCurrency("€");

			isEuroLower = entity.getLowerRange().getCurrency().equals(euro.getCurrency());
			errors.state(request, isEuroLower, "lowerRange", "consumer.offers.error.must-be-euro");

			isEuroMajor = entity.getMajorRange().getCurrency().equals(euro.getCurrency());
			errors.state(request, isEuroMajor, "majorRange", "consumer.offers.error.must-be-euro");

			if (isEuroLower && isEuroMajor) {

				isRange = entity.getMajorRange().getAmount() > entity.getLowerRange().getAmount();
				errors.state(request, isRange, "majorRange", "consumer.offers.error.notRange");
			}

		}

		isAccepted = request.getModel().getString("accept") != "" && request.getModel().getString("accept") != null;
		errors.state(request, isAccepted, "accept", "consumer.offers.error.must-accept");

	}

	@Override
	public void create(final Request<Offers> request, final Offers entity) {
		assert request != null;
		assert entity != null;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
