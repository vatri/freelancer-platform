package net.vatri.freelanceplatform.frontend.controllers;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        bid.setJob(job);
        bid.setUser(user);

        Bid savedBid = bidService.save(bid);
        return "redirect:/job/" + job.getId();
    }
}
