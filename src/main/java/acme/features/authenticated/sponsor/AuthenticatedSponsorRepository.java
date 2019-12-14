
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;

import acme.entities.creditcard.Creditcard;
import acme.entities.roles.Sponsor;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findOnesponsorByUserAccountId(int id);

	@Query("select c from Creditcard c where c.sponsor.id=?1")
	Creditcard findOneCreditCardBySponsorId(int id);
}
