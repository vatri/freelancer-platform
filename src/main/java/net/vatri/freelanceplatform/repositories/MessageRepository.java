package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByJob(Job job);

	@Query("SELECT m"
			+ " FROM Message m"
			+ " WHERE m.job = :job AND ( m.sender = :contractor OR m.receiver = :contractor )")
	List<Message> findByJobAndSenderOrReceiver(
		@Param("job") Job job,
		@Param("contractor") User contractor
	);

}