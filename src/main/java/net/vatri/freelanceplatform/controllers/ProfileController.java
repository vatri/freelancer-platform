package net.vatri.freelanceplatform.controllers;

import java.util.ArrayList;
import java.util.List;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.JobHistory;
import net.vatri.freelanceplatform.models.Profile;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.FeedbackService;
import net.vatri.freelanceplatform.services.JobService;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

    @Autowired
    UserService userService;
    
    @Autowired
    BidService bidService;
    
    @Autowired
    JobService jobService;
    
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = { "", "/{id}" })
    public String viewProfile(@PathVariable("id") Optional<Long> profileIdParam , Model model){

        Long userId = profileIdParam.isPresent() ? profileIdParam.get() : 0L;

        User loggedUser = super.getCurrentUser();

        if(loggedUser == null){
            return "redirect:/";
        }

        User user;
        boolean canEdit = false;

        // If profile ID is not provided in URL, fetch currently logged user
        if(userId < 1) {
            user = loggedUser;
            canEdit = true;
        } else {
            user = userService.get(userId);
            if(userId == loggedUser.getId()){
                canEdit = true;
            }
        }

        if(user == null){
            return "redirect:/";
        }
        
        
        List<Bid> myBids = null;
        if( canEdit ){
            myBids = bidService.findByUser(user);
        }
        
        List<Bid> closedBids = bidService.findByClosedAndUser(1, user);
       
        List<Feedback> myFeedbacks = feedbackService.findByBids(closedBids);

        
        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());
        model.addAttribute("canEdit", canEdit);
        model.addAttribute("myBids", myBids);
        model.addAttribute("my_feedbacks", myFeedbacks);

        return "frontend/profile/view_profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model){

        User user = getCurrentUser();
        model.addAttribute("user", user);

        return "frontend/profile/edit_profile";
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute @Valid User user, @ModelAttribute Profile profile, Model model){

        //
        // Get and update current logged user. Don't use params from input to prevent unauthorized editing.
        User me = getCurrentUser();
        
		me.setName(user.getName());
		me.setEmail(user.getEmail());

		if (me.getProfile() != null) {
			me.getProfile().setLinkedin(profile.getLinkedin());
			me.getProfile().setLocation(profile.getLocation());
			me.getProfile().setBiography(profile.getBiography());
		} else {
			me.setProfile(profile);
			me.getProfile().setUser(me);
		}

        userService.save(me);

        return "redirect:/profile";
    }
    
    @GetMapping("/client/{id}")
    public String viewClientProfile(@PathVariable("id") Long userId, Model model) throws Exception{
    	
    	User user = userService.get(userId);
    	if( user == null ) {
    		throw new Exception("User not found");
    	}
    	
    	List<Job> clientJobs = jobService.findByAuthor(user);

    	List<JobHistory> jobHistory = new ArrayList<>();
        
    	int totalJobs = 0;
    	int hiredJobs = jobService.findHiredJobsByAuthor(user).size();
    	
        for(Job j : clientJobs) {
        	
        	totalJobs++;
        	
        	JobHistory jh = new JobHistory();
        	Feedback feedback = feedbackService.findByJob(j);
        	
        	jh.setJob(j);
        	jh.setFeedback(feedback);
    		jobHistory.add(jh);
        	
        }

        model.addAttribute("user", user);
		model.addAttribute("profile", user.getProfile());
		model.addAttribute("total_jobs", totalJobs);
		model.addAttribute("hired_jobs", hiredJobs);
		model.addAttribute("job_history", jobHistory);
	
    	return "frontend/profile/view_client_profile";
    	
    }


}
