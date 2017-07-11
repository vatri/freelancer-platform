package net.vatri.freelanceplatform.frontend.controllers;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.CategoryService;
import net.vatri.freelanceplatform.services.JobsService;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobsService jobsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping
    public String listJobs(Model model){
        model.addAttribute("jobs", jobsService.list());
        return "frontend/job/jobs";
    }

    @GetMapping("/{id}")
    public String viewJob(Model model, @PathVariable("id") long id){
        model.addAttribute("job", jobsService.get(id));
        return "frontend/job/job";
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
        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userService.getByEmail(authUser.getUsername());

        job.setAuthor(author);

        Job savedJob = null;
        if( id != null && id > 0){
            savedJob = new Job();
            try {
                throw new Exception("ERROR");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            savedJob = jobsService.add(job);
        }
        return "redirect:/job/" + savedJob.getId();
    }

}
