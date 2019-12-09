
package acme.entities.comercialbanner;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

import acme.entities.banner.Banner;
import acme.entities.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comercialbanner extends Banner {

	// Serialization Identify ------------------------------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	//	Atributes	----------------------------------------------------------------------------------------------------------

	@NotBlank
	@CreditCardNumber
	private String creditCard;

	//	Relationships -------------------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;
}
