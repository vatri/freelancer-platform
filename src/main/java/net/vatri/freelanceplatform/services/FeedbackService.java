package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.repositories.FeedbackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

	@Autowired
	FeedbackRepository feedbackRepository;

	public Feedback save(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	public Feedback get(Long id) {
		return feedbackRepository.findOne(id);
	}
    
    public Feedback findByBid(Bid bid){
        return feedbackRepository.findByBid(bid);
    }

}
