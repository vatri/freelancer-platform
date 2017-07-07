package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Job;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long> {}
