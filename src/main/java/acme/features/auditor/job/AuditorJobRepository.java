
package acme.features.auditor.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditrecord.Auditrecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where j.auditor.id = ?1")
	Collection<Job> findManyByAuditorId(int AuditorId);

	@Query("select ar from Auditrecord ar ")
	Collection<Auditrecord> findAuditrecordsAll();

	@Query("select j from Job j ")
	Collection<Job> findAllJobs();

	@Query("select au from Auditor au where au.id = ?1")
	Auditor findAuditorJobById(int id);

	@Query("select au from Auditrecord au where au.auditor.id=?1")
	Collection<Auditrecord> findAuditRecordByAuditorId(int AuditorId);

}
