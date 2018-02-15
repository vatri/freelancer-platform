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
	
	private Integer clientRate;
	
	private String clientFeedback;

	// Use wrapper class Integer instead int primitive to avoid PropertyAccessException: Null value was assigned to a property...
	private Integer contractorRate; 
	
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

	public Integer getClientRate() {
		return clientRate;
	}

	public void setClientRate(Integer clientRate) {
		this.clientRate = clientRate;
	}

	public String getClientFeedback() {
		return clientFeedback;
	}

	public void setClientFeedback(String clientFeedback) {
		this.clientFeedback = clientFeedback;
	}

	public Integer getContractorRate() {
		return contractorRate;
	}

	public void setContractorRate(Integer contractorRate) {
		this.contractorRate = contractorRate;
	}

	public String getContractorFeedback() {
		return contractorFeedback;
	}

	public void setContractorFeedback(String contractorFeedback) {
		this.contractorFeedback = contractorFeedback;
	}
	
}