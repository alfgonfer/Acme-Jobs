
package acme.features.auditor.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditrecord.Auditrecord;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuditorAuditUpdateService implements AbstractUpdateService<Auditor, Auditrecord> {

	// Internal State --------------------------------------------------------------------------------------

	@Autowired
	private AuditorAuditrecordRepository repository;

	// AbstractUpdateService<Auditor, Auditrecord> interface -----------------------------------------------


	@Override
	public boolean authorise(final Request<Auditrecord> request) {
		assert request != null;

		boolean result;
		int AuditrecordId;
		Auditrecord Auditrecord;
		Auditor auditor;
		Principal principal;

		AuditrecordId = request.getModel().getInteger("id");
		Auditrecord = this.repository.findOneAuditrecordById(AuditrecordId);
		auditor = Auditrecord.getJob().getAuditor();
		principal = request.getPrincipal();
		result = Auditrecord.getJob().isFinalMode() || !Auditrecord.getJob().isFinalMode() && auditor.getId() == principal.getActiveRoleId();
		return result;
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

		request.unbind(entity, model, "title", "isFinalMode", "body");

	}

	@Override
	public Auditrecord findOne(final Request<Auditrecord> request) {
		assert request != null;

		Auditrecord result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneAuditrecordById(id);

		return result;
	}

	@Override
	public void validate(final Request<Auditrecord> request, final Auditrecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		boolean p = request.getModel().getBoolean("isFinalMode");
		if (!p) {
			errors.state(request, !p, "isFinalMode", "auditor.auditrecord.error.must-accept");

		}

	}

	@Override
	public void update(final Request<Auditrecord> request, final Auditrecord entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
