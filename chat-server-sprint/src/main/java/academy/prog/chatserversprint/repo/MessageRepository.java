package academy.prog.chatserversprint.repo;

import academy.prog.chatserversprint.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.id > :id AND (m.to = :to OR m.to IS NULL)")
    List<Message> findNew(long id, String to);

    @Query("SELECT DISTINCT m.from FROM Message m")
    List<String> findAllUsers();

}
