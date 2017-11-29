package net.vatri.freelanceplatform.repositories;


import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import net.vatri.freelanceplatform.models.User;


@Repository("bidRepository")
public interface BidRepository extends JpaRepository<Bid, Long> {
    List findByUserIdAndJobId(Long userId, Long jobId);
    List findByUser(User user);
    List findByJob(Job job);
}