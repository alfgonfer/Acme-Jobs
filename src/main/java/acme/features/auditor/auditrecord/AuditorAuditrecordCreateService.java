
package acme.features.auditor.auditrecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditrecord.Auditrecord;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class AuditorAuditrecordCreateService implements AbstractCreateService<Auditor, Auditrecord> {

	//Internal state --------------------------------------------------------------------------------------------------

	@Autowired
	AuditorAuditrecordRepository repository;


	// AbstractCreateService<Auditor, Auditrecord> -------------------------------------------------------------

	@Override
	public boolean authorise(final Request<Auditrecord> request) {
		assert request != null;
		boolean b = request.getPrincipal().hasRole(Auditor.class);
		return b;
	}

	@Override
	public void bind(final Request<Auditrecord> request, final Auditrecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "job");

	}

	@Override
	public void unbind(final Request<Auditrecord> request, final Auditrecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "status", "body");

	}

	@Override
	public Auditrecord instantiate(final Request<Auditrecord> request) {
		Auditrecord result;

		result = new Auditrecord();
		return result;
	}

	@Override
	public void validate(final Request<Auditrecord> request, final Auditrecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDraft = request.getModel().getString("status") != "" && request.getModel().getString("status") != null;
		errors.state(request, isDraft, "status", "auditor.auditrecord.error.must-accept");

	}

	@Override
	public void create(final Request<Auditrecord> request, final Auditrecord entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		String job = request.getModel().getString("jref");
		entity.setJob(this.repository.findJobByTitle(job));
		if (request.getModel().getString("status") != "" && request.getModel().getString("status") != null) {
			entity.setStatus(false);
		} else {
			entity.setStatus(true);
		}

		this.repository.save(entity);

	}

}
