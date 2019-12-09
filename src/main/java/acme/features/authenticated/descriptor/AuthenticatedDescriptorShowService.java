
package acme.features.authenticated.descriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.descriptor.Descriptor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedDescriptorShowService implements AbstractShowService<Authenticated, Descriptor> {

	@Autowired
	AuthenticatedDescriptorRepository repository;


	@Override
	public boolean authorise(final Request<Descriptor> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Descriptor> request, final Descriptor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description", "job.title", "jobTitle", "jobId");

	}

	@Override
	public Descriptor findOne(final Request<Descriptor> request) {
		assert request != null;
		Descriptor result;
		int id;

		id = request.getModel().getInteger("jobId");
		result = this.repository.findOneByJobId(id);
		return result;
	}

}
