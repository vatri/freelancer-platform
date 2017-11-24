package net.vatri.freelanceplatform.frontend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.CategoryService;
import net.vatri.freelanceplatform.services.JobService;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/job")
public class JobController extends AbstractController{

    @Autowired
    JobService jobService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BidService bidService;

    @GetMapping
    public String listJobs(Model model){
        model.addAttribute("jobs", jobService.list());
        return "frontend/job/jobs";
    }

    @GetMapping("/{id}")
    public String viewJob(Model model, @PathVariable("id") long id){

        Job job = jobService.get(id);

        model.addAttribute("job", job);

        // Get my bid for the job and assign to the view
        Bid myBid = null;

        // Check if logged in:
        User currentUser = super.getCurrentUser();
        if( currentUser != null){
            myBid = bidService.getUsersBidByJob(currentUser, job);
        }
        model.addAttribute("myBid", myBid);

        return "frontend/job/view_job";
    }

    @GetMapping("/create")
    public String createJob(Model model){
    	User author = super.getCurrentUser();
        if(author == null){
        	return "redirect:/";
        }
        model.addAttribute("categories", categoryService.list());
        return "frontend/job/create_job";
    }

    @PostMapping("/save")
    public String saveJob(
            @RequestParam(name = "id", required = false) Long id,
            @ModelAttribute Job job,
            Model model){

        if(job.getTitle().isEmpty() /*|| 1==1*/){
            model.addAttribute("error", "Title required");
            return "frontend/job/create_job";
        }

        // Set current loged user ID as author
        User author = super.getCurrentUser();
        if(author == null){
            System.out.println("Please login to add a job!");
            return null;
        }
        job.setAuthor(author);

        Job savedJob = null;
        if( id != null && id > 0){
// TODO
        } else {
            savedJob = jobService.add(job);
        }
        return "redirect:/job/" + savedJob.getId();
    }

}
