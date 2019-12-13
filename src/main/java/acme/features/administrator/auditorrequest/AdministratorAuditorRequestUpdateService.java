
package acme.features.administrator.auditorrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditorrequest.AuditorRequest;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorAuditorRequestUpdateService implements AbstractUpdateService<Administrator, AuditorRequest> {

	// Internal state ---------------------------------------------------------------------------------------------------

	@Autowired
	private AdministratorAuditorRequestRepository repository;

	// AbstractUpdateService<Administrator, AuditorRequest> -------------------------------------------------------------


	@Override
	public boolean authorise(final Request<AuditorRequest> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<AuditorRequest> request, final AuditorRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<AuditorRequest> request, final AuditorRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "username", "description", "status", "firm", "respStatement");

	}

	@Override
	public AuditorRequest findOne(final Request<AuditorRequest> request) {
		assert request != null;

		Integer id;
		AuditorRequest result;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneAuditorRequestById(id);

		return result;

	}

	@Override
	public void validate(final Request<AuditorRequest> request, final AuditorRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void update(final Request<AuditorRequest> request, final AuditorRequest entity) {
		assert request != null;
		assert entity != null;

		if (entity.getStatus() == "accepted") {

			Auditor auditor;
			UserAccount user;

			user = entity.getUser();

			auditor = new Auditor();
			auditor.setFirm(entity.getFirm());
			auditor.setRespStatement(entity.getRespStatement());

			auditor.setUserAccount(user);

			this.repository.save(auditor);

		}

		this.repository.save(entity);

	}

}
