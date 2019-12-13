
package acme.features.authenticated.auditorrequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditorRequestRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=?1")
	UserAccount findOneUserAccountById(int id);

}
