
package acme.features.authenticated.auditorrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditorrequest.AuditorRequest;
import acme.entities.configuration.Configuration;
import acme.features.utiles.ConfigurationRepository;
import acme.features.utiles.Spamfilter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedAuditorRequestCreateService implements AbstractCreateService<Authenticated, AuditorRequest> {

	// Internal state ---------------------------------------------------------------------------------------------------

	@Autowired
	private AuthenticatedAuditorRequestRepository	repository;
	@Autowired
	private ConfigurationRepository					configurationRepository;

	// AbstractCreateService<Authenticated, AuditorRequest> inteface ----------------------------------------------------


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
	public AuditorRequest instantiate(final Request<AuditorRequest> request) {
		assert request != null;

		AuditorRequest result;

		result = new AuditorRequest();
		result.setStatus("pending");

		return result;
	}

	@Override
	public void validate(final Request<AuditorRequest> request, final AuditorRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean hasDescription, hasSpamDescription, hasFirm, hasSpamFirm, hasRepStatement, hasSpamStatement;
		Configuration configuration;
		String spamWords;
		Double spamThreshold;

		configuration = this.configurationRepository.findConfiguration();

		spamWords = configuration.getSpamWords();
		spamThreshold = configuration.getSpamThreshold();

		hasDescription = entity.getDescription() != null && !entity.getDescription().isEmpty();
		errors.state(request, hasDescription, "description", "authenticated.auditorrequest.error.must-have-description");

		if (hasDescription) {

			hasSpamDescription = Spamfilter.spamThreshold(entity.getDescription(), spamWords, spamThreshold);
			errors.state(request, !hasSpamDescription, "description", "authenticated.auditorrequest.error.must-not-have-spam-description");
		}

		hasFirm = entity.getFirm() != null && !entity.getFirm().isEmpty();
		errors.state(request, hasDescription, "firm", "authenticated.auditorrequest.error.must-have-firm");

		if (hasFirm) {

			hasSpamFirm = Spamfilter.spamThreshold(entity.getFirm(), spamWords, spamThreshold);
			errors.state(request, !hasSpamFirm, "firm", "authenticated.auditorrequest.error.must-not-have-spam-firm");
		}

		hasRepStatement = entity.getRespStatement() != null && !entity.getRespStatement().isEmpty();
		errors.state(request, hasRepStatement, "respStatement", "authenticated.auditorrequest.error.must-have-respStatement");

		if (hasRepStatement) {

			hasSpamStatement = Spamfilter.spamThreshold(entity.getRespStatement(), spamWords, spamThreshold);
			errors.state(request, !hasSpamStatement, "respStatement", "authenticated.auditorrequest.error.must-not-have-spam-respStatement");
		}

	}

	@Override
	public void create(final Request<AuditorRequest> request, final AuditorRequest entity) {
		assert request != null;
		assert entity != null;

		Principal principal;
		Integer id;
		UserAccount user;

		principal = request.getPrincipal();
		id = principal.getAccountId();

		user = this.repository.findOneUserAccountById(id);

		entity.setUser(user);
		entity.setStatus("pending");

		this.repository.save(entity);

	}

}
