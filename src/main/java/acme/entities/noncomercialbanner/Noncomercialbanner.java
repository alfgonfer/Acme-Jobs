
package acme.entities.noncomercialbanner;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import acme.entities.banner.Banner;
import acme.entities.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Noncomercialbanner extends Banner {

	// Serialization Identify ------------------------------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	//	Atributes	----------------------------------------------------------------------------------------------------------

	@URL
	private String jingle;

	//	Relationships -------------------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}
