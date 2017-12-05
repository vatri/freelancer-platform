package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByAuthor(User author);
}
