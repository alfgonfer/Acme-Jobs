
package acme.features.authenticated.messagethread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.Message;
import acme.entities.messagethreads.Messagethread;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessagethreadRepository extends AbstractRepository {

	@Query("select m from Messagethread m where m.id = ?1")
	Messagethread findOneMessagethreadById(int id);

	@Query("select m from Message m where m.messageThread.id = ?1")
	Collection<Message> findMessagebyMessagethread(int id);

	@Query("select messagethread from UserAccount ua where ua.id=?1")
	Collection<Messagethread> findManybyUser(int id);

}
//select ua, mt from UserAccount ua join ua.messagethread mt group by mt;
