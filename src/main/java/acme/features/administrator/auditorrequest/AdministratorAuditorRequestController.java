
package acme.features.administrator.auditorrequest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.auditorrequest.AuditorRequest;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/auditorrequest/")
public class AdministratorAuditorRequestController extends AbstractController<Administrator, AuditorRequest> {

	@Autowired
	private AdministratorAuditorRequestListService		listService;

	@Autowired
	private AdministratorAuditorRequestShowService		showService;

	@Autowired
	private AdministratorAuditorRequestUpdateService	updateService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}
