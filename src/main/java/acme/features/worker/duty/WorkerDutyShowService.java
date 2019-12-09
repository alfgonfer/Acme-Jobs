
package acme.features.worker.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class WorkerDutyShowService implements AbstractShowService<Worker, Duty> {

	@Autowired
	WorkerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "percentage", "jobTitle", "jobId");

	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		Duty result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findDutyById(id);

		return result;
	}

}
