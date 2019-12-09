
package acme.features.auditor.descriptor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.descriptor.Descriptor;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class AuditorDescriptorListService implements AbstractListService<Auditor, Descriptor> {

	@Autowired
	AuditorDescriptorRepository repository;


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

		request.unbind(entity, model, "description", "job.title");

	}

	@Override
	public Collection<Descriptor> findMany(final Request<Descriptor> request) {
		assert request != null;
		Collection<Descriptor> result;
		Descriptor descriptor;
		int id;

		id = request.getModel().getInteger("jobId");
		descriptor = this.repository.findOneByJobId(id);
		//result.add(descriptor;
		return null;
	}

}
