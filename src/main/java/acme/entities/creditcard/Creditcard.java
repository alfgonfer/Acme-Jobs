
package acme.entities.creditcard;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import acme.entities.roles.Sponsor;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Creditcard extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@CreditCardNumber
	private String				creditNumber;

	@NotBlank
	private String				name;

	@NotBlank
	private String				surname;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				expiration;

	@NotBlank
	@Pattern(regexp = "^[0-9]{3}$", message = "authenticated.creditcard.error.must-have-securityCode-pattern")
	private String				securityCode;

	@NotNull
	@Valid
	@OneToOne(optional = false)
	private Sponsor				sponsor;


	// Derivated atributes
	@Transient
	public Integer getSponsorId() {
		return this.sponsor.getId();
	}

}
