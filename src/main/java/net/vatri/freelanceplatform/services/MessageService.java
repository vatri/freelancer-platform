package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	public Message save(Message message) {
		return messageRepository.save(message);
	}

	public Message get(Long id) {
		return messageRepository.findOne(id);
	}
    
    public List<Message> findByJobAndContractor(Job job, User contractor) {
    	return messageRepository.findByJobAndSenderOrReceiver(job, contractor);
    }

}
