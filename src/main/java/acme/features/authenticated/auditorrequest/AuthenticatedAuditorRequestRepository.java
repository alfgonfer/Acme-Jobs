
package acme.features.authenticated.auditorrequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditorrequest.Auditorrequest;
import acme.entities.roles.Auditor;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditorRequestRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select ar from Auditorrequest ar where ar.user.id=?1 and ar.status='pending'")
	Collection<Auditorrequest> allRequestByUserAccountId(int id);

	@Query("select ua from Auditor ua where ua.userAccount.id=?1")
	Auditor findOneAuditorByUserId(int id);

}
