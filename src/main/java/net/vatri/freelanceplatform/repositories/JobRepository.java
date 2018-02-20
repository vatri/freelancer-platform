package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByAuthor(User author);

	@Query("SELECT j"
			+ " FROM Bid b "
			+ " JOIN b.job j "
			+ " WHERE j.author = :user AND b.accepted = 1")
	List<Job> findByAuthorAndHired(@Param("user") User user);

	Page<Job> findByAuthor(User user, Pageable request);
}
