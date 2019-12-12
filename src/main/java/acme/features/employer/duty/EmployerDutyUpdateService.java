
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.duties.Duty;
import acme.entities.roles.Employer;
import acme.features.utiles.Spamfilter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerDutyUpdateService implements AbstractUpdateService<Employer, Duty> {

	// Internal State --------------------------------------------------------------------------------------

	@Autowired
	private EmployerDutyRepository repository;

	// AbstractUpdateService<Employer, Duty> interface -----------------------------------------------


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		Duty Duty;
		Principal principal;
		int idPrincipal, id;
		boolean res;

		principal = request.getPrincipal();
		idPrincipal = principal.getAccountId();
		id = request.getModel().getInteger("id");
		Duty = this.repository.findDutyById(id);

		res = idPrincipal == Duty.getDescriptor().getJob().getEmployer().getUserAccount().getId();

		return res;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "descriptor");

	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description");

	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findDutyById(id);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Configuration configuration;
		String spamWords;
		Double spamThreshold;
		boolean hasDescription, isNotSpam;

		hasDescription = entity.getDescription() != null;
		errors.state(request, hasDescription, "description", "employer.duty.error.must-have-description");

		if (hasDescription) {

			configuration = this.repository.findConfiguration();
			spamWords = configuration.getSpamWords();
			spamThreshold = configuration.getSpamThreshold();

			isNotSpam = Spamfilter.spamThreshold(entity.getDescription(), spamWords, spamThreshold);
			errors.state(request, !isNotSpam, "description", "employer.duty.error.must-not-be-spam");
		}

	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
