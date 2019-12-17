
package acme.entities.messagethreads;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Messagethread extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@NotNull
	private String				usernames;

	//Relationships

	/*
	 * @NotNull
	 *
	 * @OneToMany(mappedBy = "messageThread")
	 * private Collection<Message> messages;
	 */
	//select ua, mt from UserAccount ua join ua.messagethread mt group by mt;
}
