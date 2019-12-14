
package acme.entities.auditorrequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditorRequest extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes

	@NotBlank
	private String				firm;

	@NotBlank
	private String				respStatement;

	@NotBlank
	@Column(length = 1024)
	private String				description;

	@NotNull
	@Pattern(regexp = "^(pending)|(accepted)|(rejected)$", message = "It must be pending, accepted or rejected.")
	private String				status;

	//RelationShips

	@Valid
	@ManyToOne
	private UserAccount			user;


	// Derivated properties

	@Transient
	public String getUsername() {
		return this.user.getUsername();
	}
}
