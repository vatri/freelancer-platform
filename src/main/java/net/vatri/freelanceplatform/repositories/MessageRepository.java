package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByJob(Job job);

	@Query(  "SELECT m"
			+ " FROM Message m"
			+ " WHERE m.job = :job AND ( m.sender = :contractor OR m.receiver = :contractor )"
			+ " ORDER BY m.id DESC")
	Page<Message> findByJobAndSenderOrReceiver(
		@Param("job") Job job,
		@Param("contractor") User contractor,
		Pageable request
	);

	@Query("SELECT m"
			+ " FROM Message m"
			+ " WHERE m.sender = :user OR m.receiver = :user "
			+ " ORDER BY m.id DESC")
	List<Message> findBySenderOrReceiver(@Param("user") User me);

	@Query("SELECT m"
			+ " FROM Message m"
			+ " WHERE "
				+ "( ( m.sender = :me AND m.receiver = :converser ) "
				+ " OR "
				+ " ( m.sender = :converser AND m.receiver = :me ) ) "
				+ " AND m.job is null "
			+ " ORDER BY m.id DESC")
	List<Message> findByMyConversers(@Param("me") User me, @Param("converser") User converser);

}