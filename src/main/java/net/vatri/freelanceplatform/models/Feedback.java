package net.vatri.freelanceplatform.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "bid_id", nullable = true)
	private Bid bid;
	
	private int clientRate;
	
	private String clientFeedback;

	private int contractorRate;
	
	private String contractorFeedback;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public int getClientRate() {
		return clientRate;
	}

	public void setClientRate(int clientRate) {
		this.clientRate = clientRate;
	}

	public String getClientFeedback() {
		return clientFeedback;
	}

	public void setClientFeedback(String clientFeedback) {
		this.clientFeedback = clientFeedback;
	}

	public int getContractorRate() {
		return contractorRate;
	}

	public void setContractorRate(int contractorRate) {
		this.contractorRate = contractorRate;
	}

	public String getContractorFeedback() {
		return contractorFeedback;
	}

	public void setContractorFeedback(String contractorFeedback) {
		this.contractorFeedback = contractorFeedback;
	}
	
}