
package acme.features.authenticated.messagethread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.Message;
import acme.entities.messagethreads.Messagethread;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessagethreadRepository extends AbstractRepository {

	@Query("select m from Messagethread m where m.id = ?1")
	Messagethread findOneMessagethreadById(int id);

	@Query("select m from Message m where m.messageThread.id = ?1")
	Collection<Message> findMessagebyMessagethread(int id);

	@Query("select messagethread from UserAccount ua where ua.id=?1")
	Collection<Messagethread> findManybyUser(int id);

	@Query("select u from UserAccount u where u.username = ?1")
	UserAccount findUserByUserName(String username);

}
//select ua, mt from UserAccount ua join ua.messagethread mt group by mt;
//select m from Messagethread m where m.title='Hilo de mensajes 1' and exists (select messagethread from UserAccount us
//where ua.id='44');
