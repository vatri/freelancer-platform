package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Message;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;
	
	@Value("${freelancer.message_room.page_size}")
    private int messageRoomPageSize;
    
	public Message save(Message message) {
		return messageRepository.save(message);
	}

	public Message get(Long id) {
		return messageRepository.findOne(id);
	}
    
    public List<Message> findByJobAndContractor(Job job, User contractor) {
    	
    	int pageNumber = 1;
    	
		PageRequest request = new PageRequest(pageNumber - 1, messageRoomPageSize, Sort.Direction.DESC, "id");

    	Page<Message> messages = messageRepository.findByJobAndSenderOrReceiver(job, contractor, request);
    	return messages.getContent();
    	
    }

	public List<Message> getRoomsByUser(User me) {
		List<Message> allMessages = messageRepository.findBySenderOrReceiver(me);
		List<Message> result = new ArrayList<Message>();
		//todo: filter out duplicated (show as rooms)
		
		Map<String,Message> uniqueRooms = new HashMap<String, Message>();
		
		
		allMessages.forEach(m -> {
			
			// Unique hash map key "job-contributor"
			String key = m.getJob() != null ? String.valueOf( m.getJob().getId()) : "X";
			key += '-';
			key += (m.getReceiver().getId() == me.getId() ? m.getSender().getId() : m.getReceiver().getId());
			
			// If room not exist in result add it to unique list
			Message m2 = uniqueRooms.get(key);
			if(m2 == null) {
				uniqueRooms.put(key, m);
			}
			
		});
		
		for(String t_key  : uniqueRooms.keySet()) {
			result.add(uniqueRooms.get(t_key));
		}
		
//		return allMessages;
		return result;
	}

	public List<Message> findByMyConversers(User me, User converser) {
		return messageRepository.findByMyConversers(me,converser);
	}

}
