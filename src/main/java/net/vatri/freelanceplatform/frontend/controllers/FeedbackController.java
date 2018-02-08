package net.vatri.freelanceplatform.frontend.controllers;

import net.vatri.freelanceplatform.helpers.FreelancePlatformHelper;
import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.FeedbackService;
import net.vatri.freelanceplatform.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends AbstractController{
	
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
		
		// TODO: can post (owner or contractor)
		
		Feedback feedback = feedbackService.findByBid(bid);
		
		System.out.println(new ObjectMapper().writeValueAsString(feedback));

		model.addAttribute("bid", bid);
		
		return "frontend/feedback/send";
	}
   	
   	@PostMapping("/save")
	public String save(@RequestParam(name = "bid_id", required = true) Long bidId, 
			@RequestParam(name = "rate", required = true) int rate, 
			@RequestParam(name = "feedback", required = true) String feedbackText){
   		
   		User me = getCurrentUser();
   		Bid bid = bidService.get(bidId);
   		// todo: I have rights?
   		Feedback feedback = new Feedback();
   		feedback.setBid(bid);
   		
   		// If I am contractor (owner of the bid), set client rate&feedback. 
   		// Otherwise, I am job owner and I set contractor rate&feedback.
   		if(me.getId().equals(  bid.getUser().getId()  )) {
	   		feedback.setClientFeedback(feedbackText);
	   		feedback.setClientRate(rate);
   		} else {
   			feedback.setContractorFeedback(feedbackText);
   			feedback.setContractorRate(rate);
   		}
   		
   		// If feedback for the bid exists, update it. Otherwise, insert.
   		Feedback dbFeedback = null;
   		
   		dbFeedback = feedbackService.findByBid(bid);
   		if(dbFeedback == null) {
System.out.println("\n\n INSERTING... \n\n");
   			dbFeedback = feedbackService.save(feedback);
   		} else {
System.out.println("\n\n UPDATING... \n\n");
   			dbFeedback = feedbackService.save(dbFeedback);
   		}
   		
   		return "redirect:/feedback/" + bidId;
   	}
   
}
