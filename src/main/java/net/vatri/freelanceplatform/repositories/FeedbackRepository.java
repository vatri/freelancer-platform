package net.vatri.freelanceplatform.repositories;


import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("feedbackRepository")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByBid(Bid bid);
}