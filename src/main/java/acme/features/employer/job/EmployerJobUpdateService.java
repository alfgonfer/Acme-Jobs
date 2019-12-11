
package acme.features.employer.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.features.utiles.Spamfilter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerJobUpdateService implements AbstractUpdateService<Employer, Job> {

	// Internal State --------------------------------------------------------------------------

	@Autowired
	private EmployerJobRepository repository;

	// AbstractUpdateService<Employer, Job> interface -----------------------------------------------------


	@Override

	public boolean authorise(final Request<Job> request) {
		assert request != null;

		Principal principal;
		int principalId, id;
		Job job;
		boolean res;

		principal = request.getPrincipal();
		principalId = principal.getAccountId();
		id = request.getModel().getInteger("id");
		job = this.repository.findOneJobById(id);

		res = principalId == job.getEmployer().getUserAccount().getId();

		return res;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadline", "salary", "moreInfo");

	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;
		int id;
		Job result;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneJobById(id);

		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean hasTitle, hasSpamTitle;
		boolean hasSalary, isEuro, hasDeadline, isFuture, hasReference, isDuplicated;

		Configuration configuration = this.repository.findConfiguration();
		String spamWords = configuration.getSpamWords();
		Double spamThreshold = configuration.getSpamThreshold();
		Date now = new Date(System.currentTimeMillis() - 1);

		// Validation title ----------------------------------------------------------------------------------------------------------
		hasTitle = entity.getTitle() != null;
		errors.state(request, hasTitle, "title", "employer.job.error.must-have-title");

		if (hasTitle) {
			hasSpamTitle = Spamfilter.spamThreshold(entity.getTitle(), spamWords, spamThreshold);
			errors.state(request, !hasSpamTitle, "title", "employer.job.error.must-not-have-spam");
		}

		// Validation salary ----------------------------------------------------------------------------------------------------------

		hasSalary = entity.getSalary() != null;
		errors.state(request, hasSalary, "salary", "employer.job.error.must-have-salary");

		if (hasSalary) {
			Money euro = new Money();
			euro.setCurrency("€");

			isEuro = entity.getSalary().getCurrency().equals(euro.getCurrency());
			errors.state(request, isEuro, "salary", "employer.job.error.must-have-salary");

		}

		// Validation deadline ----------------------------------------------------------------------------------------------------------

		hasDeadline = entity.getDeadline() != null;
		errors.state(request, hasDeadline, "deadline", "employer.job.error.must-have-deadline");

		if (hasDeadline) {
			isFuture = entity.getDeadline().after(now);
			errors.state(request, isFuture, "deadline", "employer.job.error.must-be-future");

		}

		// Validation reference ----------------------------------------------------------------------------------------------------------

		hasReference = entity.getReference() != null;
		errors.state(request, hasReference, "reference", "employer.job.error.must-have-reference");

		if (hasReference) {

			isDuplicated = this.repository.findOneJobByReference(entity.getReference()) == null;
			errors.state(request, isDuplicated, "reference", "employer.job.error.must-be-not-duplicated-reference");
		}

	}

	@Override
	public void update(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
