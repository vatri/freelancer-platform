package net.vatri.freelanceplatform.controllers;

import net.vatri.freelanceplatform.helpers.FreelancePlatformHelper;
import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Feedback;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
import net.vatri.freelanceplatform.services.CategoryService;
import net.vatri.freelanceplatform.services.FeedbackService;
import net.vatri.freelanceplatform.services.JobService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
    
    @Autowired
    FeedbackService feedbackService;
    
    @Value( "${freelancer.job.page_size}" )
    private int jobPageSize;
    
//    @GetMapping
//    public String listJobs(Model model, HttpServletRequest request){
//    	
//    	String filt = request.getParameter("filter");
//    	User me = getCurrentUser();
//    	boolean isMyJobsPage = false;
//    	
//    	if( filt != null && filt.equals("myjobs") && me != null) {
//    		Map<String, Object> filter = new HashMap<>();
//    		filter.put("user", me );
//            model.addAttribute("jobs", jobService.list(filter));
//            
//            isMyJobsPage = true;
//    	} else {
//    		model.addAttribute("jobs", jobService.list());
//    	}
//    	
//    	model.addAttribute("isMyJobsPage", isMyJobsPage);
//    	
//        return "frontend/job/jobs";
//    }
    
    @GetMapping
    public String listJobs2(Model model, HttpServletRequest request){
    	
    	String pageUrl = "/job?a=a"; // Warning: In the HTML template we will append &page=[page] and we need to have ? in the query string
    	
    	String filt = request.getParameter("filter");
    	String pPage = request.getParameter("page");
    	
    	User me = getCurrentUser();
    	boolean isMyJobsPage = false;
    	
    	Map<String, Object> filter = new HashMap<>();

    	if( filt != null && filt.equals("myjobs") && me != null) {
    		
    		filter.put("user", me );
    		pageUrl = "/job?filter=myjobs";
            isMyJobsPage = true;
    	
    	}
    	
    	int pageNo = 1;
    	if( pPage != null ) {
    		pageNo = Integer.parseInt(pPage);
    	}
    	
    	Page<Job> jobsPage = jobService.findAllPaged(filter, pageNo, jobPageSize);
    	
    	model.addAttribute("is_my_jobs_page", isMyJobsPage);
    	model.addAttribute("jobs_page", jobsPage);
    	model.addAttribute("page_url", pageUrl);
    	
        return "frontend/job/jobs";
    }

    @GetMapping({"/view/{id}", "/{id}" })
    public String viewJob(Model model, @PathVariable("id") long id){

        Job job = jobService.get(id);

        model.addAttribute("job", job);

        // Get my bid for the job and assign to the view
        Bid myBid = null;

        // Check if logged in:
        User currentUser = super.getCurrentUser();
        if( currentUser != null){
            myBid = bidService.getUsersBidByJob(currentUser, job);
            if(myBid != null) {
	        	// New line to <br>
	            myBid.setProposal(FreelancePlatformHelper.nl2br(myBid.getProposal()));
            }
        }

        model.addAttribute("myBid", myBid);
        
        model.addAttribute("me", getCurrentUser());

        // Calculate client rate:
        long avgClientFeedback = 0;
		int totalFeedbackNo = 0;
        List<Feedback> feedbacks = feedbackService.findByClient(job.getAuthor());
        if(feedbacks.size() > 0) {
			int sum = 0;
			int no = 0;
			for (Feedback f : feedbacks) {
				sum += f.getClientRate();
				no++;
			}
			avgClientFeedback = sum / no;
			totalFeedbackNo = feedbacks.size();
        }
		// Calculate hire rate:
		List<Job> jobs = jobService.findByAuthor(job.getAuthor());
		List<Job> hiredJobs = jobService.findHiredJobsByAuthor(job.getAuthor());
		double totalJobsNo = jobs.size();
		double hiredJobsNo = hiredJobs.size();
		double hireRate = (hiredJobsNo / totalJobsNo) * 100; // percent

		model.addAttribute("average_client_feedback_rate", avgClientFeedback);
		model.addAttribute("reviews_no", totalFeedbackNo);
		model.addAttribute("bids_no", bidService.findByJob(job).size());
		model.addAttribute("hire_rate", (int) hireRate);
		model.addAttribute("jobs_no", (int) totalJobsNo);
		model.addAttribute("hired_jobs_no", (int) hiredJobsNo);

		return "frontend/job/view_job";
    }

    @GetMapping("/create")
    public String createJob(Model model){
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
        return "redirect:/job/view/" + savedJob.getId();
    }

    @GetMapping("/bids/{jobId}")
    public String viewBids(Model model, @PathVariable("jobId") long jobId) {
    	
    	Job job = jobService.get(jobId);
    	
    	User me = getCurrentUser();
    	
    	if( job == null || job.getAuthor().getId() != me.getId() ) {
    		System.out.println("Job not found or you don't have privileges");
    		return "redirect:/job/view/" + jobId;
    	}
    	
    	List<Bid> bids = bidService.findByJob(job);
    	
    	model.addAttribute("job", job);
    	model.addAttribute("bids", bids);
    	
    	// If me != author
    	return "frontend/job/view_bids";
    }

   

}
