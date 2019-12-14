
package acme.features.authenticated.creditcard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.creditcard.Creditcard;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedCreditCardRepository extends AbstractRepository {

	@Query("select c from Creditcard c where c.sponsor.id=?1")
	Creditcard findOneCreditCardBySponsorId(int id);

	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findOnesponsorByUserAccountId(int id);
}
