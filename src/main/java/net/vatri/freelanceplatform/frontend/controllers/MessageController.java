package net.vatri.freelanceplatform.frontend.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.BidService;
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
	
	@Autowired
	BidService bidService;
	
	@GetMapping
	public String myMessageRooms(Model model) {
		
		User me = getCurrentUser();
		List<Message> messages = messageService.getRoomsByUser(me);
		
		model.addAttribute("messages", messages);
		
		model.addAttribute("my_id", me.getId());
		
		return "frontend/message/my_message_rooms";
	}

	@GetMapping("/job_room/{jobId}/{contractor}")
	public String jobRoom(Model model, 
						  @PathVariable("jobId") long jobId,
						  @PathVariable("contractor") long contractorId) {

		User me = getCurrentUser();
		
		Job job = jobService.get(jobId);
		model.addAttribute("job", job);
		
		User contractor = userService.get(contractorId);
		model.addAttribute("contact", contractor);

		Bid bid = job.getAuthor().getId() == me.getId() 
				? bidService.getUsersBidByJob(contractor, job)
				: bidService.getUsersBidByJob(me, job);
		model.addAttribute("bid", bid);
		
		List<Message> messages = messageService.findByJobAndContractor(job, contractor);
		messages.sort( (Message o1, Message o2) -> {
			return o2.getId().compareTo(o1.getId());
		});
		model.addAttribute("messages", messages);

		return "frontend/message/job_room";
	}
	
	@PostMapping("/job_room/{jobId}/{contractor}")
	public String sendMessageToJobRoom(
			HttpServletRequest request,
			@PathVariable("jobId") long jobId,
			@PathVariable("contractor") long contractorId) throws Exception {
		
		Job job = jobService.get(jobId);
		User contractor = userService.get(contractorId);
		User me = getCurrentUser();
		
		// Check if I have rights to add message:
		if(job.getAuthor().getId() != me.getId() && job.getAuthor().getId() != contractor.getId() ){
			throw new Exception("Current user can not write message to this job");
		}
		
		String messageText = request.getParameter("message");
		
		Message message = new Message();
		message.setJob(job);
		message.setReceiver(contractor);
		message.setSender(getCurrentUser());
		message.setText(messageText);
		message.setCreated( new Date() );

		Message result = messageService.save(message);
		if(result == null) {
			throw new Exception("Can't save new message");
		}
		
		return "redirect:" + request.getHeader("Referer");
	
	}
}
