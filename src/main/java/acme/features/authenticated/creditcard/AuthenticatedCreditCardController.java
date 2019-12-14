
package acme.features.authenticated.creditcard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.creditcard.Creditcard;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/creditcard/")
public class AuthenticatedCreditCardController extends AbstractController<Authenticated, Creditcard> {

	// Internal State --------------------------------------------------------------------------------------

	@Autowired
	private AuthenticatedCreditCardUpdateService updateService;


	// Constructors -----------------------------------------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);

	}

}
