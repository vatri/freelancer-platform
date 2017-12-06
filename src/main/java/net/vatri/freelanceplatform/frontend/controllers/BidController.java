package net.vatri.freelanceplatform.frontend.controllers;

import net.vatri.freelanceplatform.helpers.FreelancePlatformHelper;
import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/bid")
public class BidController extends AbstractController{

    @Autowired
    BidService bidService;

    @Autowired
    JobService jobService;


    @PostMapping("/save")
    public String saveBid(@Valid Bid bid, @RequestParam("job_id") Long jobId){

        User user = super.getCurrentUser();

        if( user == null){
            return "redirect:/";
        }

        Job job = jobService.get(jobId);
        
        String created = FreelancePlatformHelper.getCurrentMySQLDate();

        bid.setJob(job);
        bid.setUser(user);
        bid.setCreated(created);

        bidService.save(bid);
        return "redirect:/job/view/" + job.getId();
    }
    
    @GetMapping("/accept/{bidId}")
    public String acceptBid(Model model, @PathVariable("bidId") long bidId) throws Exception{
    	
    	Bid bid = bidService.get(bidId);
    	
    	User me = getCurrentUser();
    	
    	// If bid was not found or if I am NOT owner of the job for which bid is placed. 
    	if( bid == null || me.getId() != bid.getJob().getAuthor().getId() ) {
//    		return "redirect:" + request.getHeader("Referer");
    		throw new Exception("No bid or no permissions");
    	}
    	
    	boolean saved = bidService.acceptBid(bid);
    	
    	if( ! saved ) {
    		throw new Exception("Bid can't be saved!");
    	}
    	
    	return "redirect:/message/job_room/" + bid.getJob().getId() + "/" + bid.getUser().getId();
    	
    }
}
