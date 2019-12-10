
package acme.features.employer.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
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

		request.bind(entity, errors, "deadline", "salary");

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

	}

	@Override
	public void update(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

	}

}
