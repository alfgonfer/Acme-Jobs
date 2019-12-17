
package acme.features.administrator.comercialbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.comercialbanner.Comercialbanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorComercialbannerShowService implements AbstractShowService<Administrator, Comercialbanner> {

	@Autowired
	AdministratorComercialbannerRepository repository;


	@Override
	public boolean authorise(final Request<Comercialbanner> request) {
		assert request != null;

		boolean res;
		Integer id;
		Comercialbanner result;
		Principal principal;
		Integer idPrincipal;
		Sponsor sponsor;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		principal = request.getPrincipal();
		idPrincipal = principal.getAccountId();

		sponsor = this.repository.findOneSponsorByUserAccountId(idPrincipal);

		res = result.isFinalMode() || sponsor.getId() == result.getSponsor().getId();

		return res;
	}

	@Override
	public void unbind(final Request<Comercialbanner> request, final Comercialbanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "urlPicture", "slogan", "urlTarget", "creditNumber");
	}

	@Override
	public Comercialbanner findOne(final Request<Comercialbanner> request) {
		assert request != null;

		Comercialbanner result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

}
