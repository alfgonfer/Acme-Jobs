
package acme.entities.investorsrecords;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Investorsrecords extends DomainEntity {
	// Serialization Identify ------------------------------------------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//	Atributes	----------------------------------------------------------------------------------------------------------

	@NotBlank
	private String				name;

	@NotBlank
	private String				sector;

	@NotBlank
	@Column(length = 1024)
	private String				statement;

	@Min(0)
	@Max(5)
	private Integer				numberStars;

	// Derivated Atributes -------------------------------------------------------------
	/*
	 * public String getNumberStars() {
	 * String stars = "";
	 * if (this.numberStars != null) {
	 * switch (this.numberStars) {
	 * case 0:
	 * stars = "-";
	 * break;
	 *
	 * case 1:
	 * stars = "★";
	 * break;
	 *
	 * case 2:
	 * stars = "★★";
	 * break;
	 *
	 * case 3:
	 * stars = "★★★";
	 * break;
	 *
	 * case 4:
	 * stars = "★★★★";
	 * break;
	 *
	 * case 5:
	 * stars = "★★★★★";
	 * break;
	 * }
	 *
	 * }
	 * return stars;
	 * }
	 */
}
