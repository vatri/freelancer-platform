package net.vatri.freelanceplatform.repositories;


import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import net.vatri.freelanceplatform.models.User;


@Repository("bidRepository")
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByUserIdAndJobId(Long userId, Long jobId);
    List<Bid> findByUser(User user);
    List<Bid> findByJob(Job job);
    
    @Query("SELECT b"
			+ " FROM Bid b"
			+ " JOIN b.job j"
			+ " WHERE j.author = :me ")
	List<Bid> findByUserJobs(@Param("me") User me);
	
    List<Bid> findByClosedAndUser(int closed, User user);
    
}