package net.vatri.freelanceplatform.frontend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.JobService;
import net.vatri.freelanceplatform.services.MessageService;
import net.vatri.freelanceplatform.services.UserService;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	MessageService messageService;

	@Autowired
	JobService jobService;
	
	@Autowired
	UserService userService;

	@GetMapping("/job_room/{jobId}/{contractor}")
	public String jobRoom(Model model, 
						  @PathVariable("jobId") long jobId,
						  @PathVariable("contractor") long contractorId) {

		Job job = jobService.get(jobId);
		model.addAttribute("job", job);
		
		User contractor = userService.get(contractorId);
		model.addAttribute("contact", contractor);

		List<Message> messages = messageService.findByJobAndContractor(job, contractor);
		model.addAttribute("messages", messages);

		return "frontend/message/job_room";
	}
}
