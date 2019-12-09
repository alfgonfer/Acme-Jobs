
package acme.features.provider.request;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.request.Request;
import acme.entities.roles.Provider;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;

@Service
public class ProviderRequestCreateService implements AbstractCreateService<Provider, Request> {

	//	Internal State -------------------------------------------------------------------------------------------------------------------
	@Autowired
	ProviderRequestRepository repository;


	// Abstract<Provider, Request> -------------------------------------------------------------------------------------------------------

	@Override
	public boolean authorise(final acme.framework.components.Request<Request> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final acme.framework.components.Request<Request> request, final Request entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "accept", "moment");

	}

	@Override
	public void unbind(final acme.framework.components.Request<Request> request, final Request entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "description", "reward", "deadLine", "ticker");

	}

	@Override
	public Request instantiate(final acme.framework.components.Request<Request> request) {
		Request result;

		result = new Request();
		return result;
	}

	@Override
	public void validate(final acme.framework.components.Request<Request> request, final Request entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date dateNow, deadLine;
		boolean isDuplicated, hasTicker, isAccepted, isFuture, isEuro, hasReward, hasDeadline;

		// Validación deadLine -----------------------------------------------------------------------------

		dateNow = new Date(System.currentTimeMillis() - 1);

		hasDeadline = entity.getDeadLine() != null;
		errors.state(request, hasDeadline, "deadLine", "provider.request.error.must-not-be-null");

		if (hasDeadline) {
			deadLine = entity.getDeadLine();
			isFuture = dateNow.before(deadLine);
			errors.state(request, isFuture, "deadLine", "provider.request.error.must-be-future");

		}

		// Validación checkbox -----------------------------------------------------------------------------

		isAccepted = request.getModel().getString("accept") != "" && request.getModel().getString("accept") != null;
		errors.state(request, isAccepted, "accept", "provider.request.error.must-accept");

		// Validación ticker -----------------------------------------------------------------------------

		hasTicker = entity.getTicker() != null;
		errors.state(request, hasTicker, "ticker", "provider.request.error.tickerDuplicated", "");

		if (hasTicker) {

			isDuplicated = this.repository.findOneByTicker(entity.getTicker()) != null;
			errors.state(request, !isDuplicated, "ticker", "provider.request.error.tickerDuplicated");
		}

		// Validación reward -----------------------------------------------------------------------------

		hasReward = entity.getReward() != null;
		errors.state(request, hasReward, "reward", "provider.request.error.not-reward");

		if (hasReward) {
			Money euro = new Money();
			euro.setCurrency("€");

			isEuro = entity.getReward().getCurrency().equals(euro.getCurrency());
			errors.state(request, isEuro, "reward", "provider.request.error.must-be-euro");

		}
	}

	@Override
	public void create(final acme.framework.components.Request<Request> request, final Request entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
