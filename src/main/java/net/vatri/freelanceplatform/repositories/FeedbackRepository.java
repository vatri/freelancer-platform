package net.vatri.freelanceplatform.repositories;


import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("feedbackRepository")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
	Feedback findByBid(Bid bid);

    @Query("SELECT f"
			+ " FROM Feedback f "
			+ " JOIN f.bid b "
			+ " JOIN b.job j "
			+ " JOIN j.author u"
			+ " WHERE u = :user ")
	List<Feedback> getClientFeedbacks(@Param("user") User user);
}