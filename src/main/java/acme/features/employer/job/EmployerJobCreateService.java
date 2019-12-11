
package acme.features.employer.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.descriptor.Descriptor;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.features.utiles.ConfigurationRepository;
import acme.features.utiles.Spamfilter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerJobCreateService implements AbstractCreateService<Employer, Job> {

	// Internal state ------------------------------------------------------------------------

	@Autowired
	private EmployerJobRepository	repository;

	@Autowired
	private ConfigurationRepository	repositoryConfiguration;


	// AbstractCreateService<Employer, Job> interface ----------------------------------------
	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "description");

	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "reference", "title", "deadline", "salary", "moreInfo");

	}

	@Override
	public Job instantiate(final Request<Job> request) {
		assert request != null;

		 Job result = new Job();

		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean hasTitle, hasSpamTitle, hasDescriptor, hasSpamDescriptor, hasReference, isDuplicated;
		boolean hasSalary, isEuro, hasDeadline, isFuture;

		String descripcion = request.getModel().getString("description").trim();
		Configuration configuration = this.repositoryConfiguration.findConfiguration();
		String spamWords = configuration.getSpamWords();
		Double spamThreshold = configuration.getSpamThreshold();
		Date now = new Date(System.currentTimeMillis() - 1);

		// Validation title ----------------------------------------------------------------------------------------------------------
		hasTitle = entity.getTitle() != null;
		errors.state(request, hasTitle, "title", "employer.job.error.must-have-title");

		if (hasTitle) {
			hasSpamTitle = Spamfilter.spamThreshold(entity.getTitle(), spamWords, spamThreshold);
			errors.state(request, !hasSpamTitle, "title", "employer.job.error.must-not-have-spam-title");
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

		// Validation descriptor --------------------------------------------------------------------------------------------------------

		hasDescriptor = descripcion != null && descripcion != "";
		errors.state(request, hasDescriptor, "description", "employer.job.error.must-have-descriptor");
		if (hasDescriptor) {
			hasSpamDescriptor = Spamfilter.spamThreshold(descripcion, spamWords, spamThreshold);
			errors.state(request, !hasSpamDescriptor, "description", "employer.job.error.must-not-have-spam-descriptor");
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
	public void create(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		Descriptor descriptor;
		String description;
		Employer employer;
		Principal principal;
		int employerId;

		principal = request.getPrincipal();
		employerId = principal.getActiveRoleId();
		employer = this.repository.findOneEmployerById(employerId);

		descriptor = new Descriptor();
		description = request.getModel().getString("description");

		descriptor.setDescription(description);
		descriptor.setJob(entity);

		entity.setEmployer(employer);

		this.repository.save(descriptor);
		this.repository.save(entity);

	}

}
