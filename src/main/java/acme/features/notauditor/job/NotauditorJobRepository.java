
package acme.features.notauditor.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditrecord.Auditrecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface NotauditorJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where not j.auditor.id = ?1 and j.finalMode = true")
	Collection<Job> findManyByAuditorId(int AuditorId);

	@Query("select ar from Auditrecord ar where not ar.auditor.id=?1")
	Collection<Auditrecord> findManyNotAuditRecordByAuditorId(int AuditorId);

	@Query("select j from Job j where j.finalMode=true")
	Collection<Job> findAllJobs();

	@Query("select au from Auditor au where au.id = ?1")
	Auditor findAuditorJobById(int id);

	@Query("select ar from Auditrecord ar ")
	Collection<Auditrecord> findAuditrecordsAll();
}
