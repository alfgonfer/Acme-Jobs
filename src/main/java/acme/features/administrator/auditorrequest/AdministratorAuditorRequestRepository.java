
package acme.features.administrator.auditorrequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditorrequest.AuditorRequest;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAuditorRequestRepository extends AbstractRepository {

	@Query("select ar from AuditorRequest ar")
	Collection<AuditorRequest> findAllAuditorsRequest();

	@Query("select ar from AuditorRequest ar where ar.id=?1")
	AuditorRequest findOneAuditorRequestById(int id);

}
