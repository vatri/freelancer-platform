package net.vatri.freelanceplatform.frontend.controllers;


import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.services.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@EnableAutoConfiguration
@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobsService jobsService;

    @GetMapping
    public String listJobs(Model model){
        model.addAttribute("jobs", jobsService.list());
        return "frontend/jobs";
    }

    @GetMapping("/{id}")
    public String viewJob(Model model, @PathVariable("id") long id){
        model.addAttribute("job", jobsService.get(id));
        return "frontend/job";
    }

    @GetMapping("/create")
    public String createJob(){
        return "frontend/create_job";
    }

    @PostMapping("/save/{id}")
    public String saveJob(@PathVariable("id") long id, @RequestBody Job job){
        Job savedJob = jobsService.add(job);
        return "redirect:job/added/" + savedJob.getId();
    }

}
