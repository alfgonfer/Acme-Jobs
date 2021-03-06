
package acme.features.notauditor.job;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditrecord.Auditrecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class NotauditorJobListMineService implements AbstractListService<Auditor, Job> {

	@Autowired
	private NotauditorJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadline");

	}

	@Override
	public Collection<Job> findMany(final Request<Job> request) {
		assert request != null;
		Collection<Job> res = new ArrayList<Job>();
		Collection<Auditrecord> result;
		Principal principal;
		Integer idPrincipal;
		Auditor auditor;

		principal = request.getPrincipal();
		idPrincipal = principal.getActiveRoleId();
		result = this.repository.findAuditrecordsAll();
		res = this.repository.findAllJobs();
		auditor = this.repository.findAuditorJobById(idPrincipal);

		for (Auditrecord ar : result) {
			if (ar.getAuditor().equals(auditor) && res.contains(ar.getJob())) {

				res.remove(ar.getJob());

			}
		}

		return res;
	}

}
