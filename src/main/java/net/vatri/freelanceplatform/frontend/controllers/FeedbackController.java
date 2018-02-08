package net.vatri.freelanceplatform.frontend.controllers;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends AbstractController{
	
	private final String ROLE_CLIENT = "client";
	private final String ROLE_CONTR = "contractor";
	
	@Autowired
	BidService bidService;
	
	@Autowired
	FeedbackService feedbackService;

   	@GetMapping("/{bidId}")
	public String send(@PathVariable("bidId") long bidId, Model model) throws Exception {

   		Bid bid = bidService.get(bidId);
   		
		if (bid == null || bid.getClosed() == 1) {
			throw new Exception("Bid closed or not exists");
		}
		
		// If user is NOT owner or contractor, he can't post:
		if( ! iCanPost(bid)) {
			throw new Exception("Not owner of the job nor contractor!");
		}
		
		Feedback feedback = feedbackService.findByBid(bid);
		
		System.out.println(new ObjectMapper().writeValueAsString(feedback));

		model.addAttribute("bid", bid);
		
		return "frontend/feedback/send";
	}
   	
   	@PostMapping("/save")
	public String save(@RequestParam(name = "bid_id", required = true) Long bidId, 
			@RequestParam(name = "rate", required = true) int rate, 
			@RequestParam(name = "feedback", required = true) String feedbackText) throws Exception{
   		
   		if(rate < 1 || rate > 5) {
   			throw new Exception("Rate must be between 1 and 5");
   		}
   		if(feedbackText.length() < 5) {
   			throw new Exception("Please enter feedback text");
   		}
   		
   		Bid bid = bidService.get(bidId);
   		
   		if( ! iCanPost(bid)) {
   			throw new Exception("Not owner of the job nor contractor!");
   		}
   		
   		User me = getCurrentUser();
   		
   		Feedback feedback = new Feedback();
   		feedback.setBid(bid);
   		
   		String myRoleForBid = me.getId().equals(bid.getUser().getId()) ? ROLE_CONTR : ROLE_CLIENT;
   		
   		// If I am contractor (owner of the bid), set client rate&feedback. 
   		// Otherwise, I am job owner and I set contractor rate&feedback.
   		if(myRoleForBid.equals(ROLE_CONTR)) {
	   		feedback.setClientFeedback(feedbackText);
	   		feedback.setClientRate(rate);
   		} else {
   			feedback.setContractorFeedback(feedbackText);
   			feedback.setContractorRate(rate);
   		}
   		
   		// If feedback for the bid exists, update it. Otherwise, insert new.
   		Feedback dbFeedback = feedbackService.findByBid(bid);
   		if(dbFeedback == null) {
   			dbFeedback = feedbackService.save(feedback); // Insert
   		} else {
   			
   	   		if(myRoleForBid.equals(ROLE_CONTR)) {
   		   		dbFeedback.setClientFeedback( feedback.getClientFeedback() );
   		   		dbFeedback.setClientRate(feedback.getClientRate() );
   	   		} else {
   	   			dbFeedback.setContractorFeedback(feedback.getContractorFeedback());
   	   			dbFeedback.setContractorRate(feedback.getContractorRate());
   	   		}

   			dbFeedback = feedbackService.save(dbFeedback); // Update

   		}
   		
   		return "redirect:/bid/my-contracts";
   	}
   	
   	private boolean iCanPost(Bid bid) {
   		User me = getCurrentUser();
   		return me.getId() != bid.getUser().getId() || me.getId() != bid.getJob().getAuthor().getId();
   	}
   
}
