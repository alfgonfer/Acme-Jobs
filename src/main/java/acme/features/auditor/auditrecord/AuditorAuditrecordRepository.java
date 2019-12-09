
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditrecord.Auditrecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditrecordRepository extends AbstractRepository {

	@Query("select at from Auditrecord at where at.id = ?1")
	Auditrecord findOneAuditrecordById(int id);

	@Query("select at from Auditrecord at where at.job.id = ?1")
	Collection<Auditrecord> findManyByJobId(int jobId);
}
