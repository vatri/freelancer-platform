package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Bid;
import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

	@Autowired
	BidRepository bidRepository;

	public Bid save(Bid bid) {
		return bidRepository.save(bid);
	}

	public Bid get(Long id) {
		return bidRepository.findOne(id);
	}

	public Bid getUsersBidByJob(User user, Job job) {

		List<Bid> bids = bidRepository.findByUserIdAndJobId(user.getId(), job.getId());

		if (bids.size() > 1) {
			System.out.println("ERROR: found more more than 1 user's bids for a job.");
		}

		try {
			return bids.get(0);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No bids found for this user");
		}
		return null;
	}

}
