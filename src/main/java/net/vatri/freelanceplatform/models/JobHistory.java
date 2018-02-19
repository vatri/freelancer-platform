package net.vatri.freelanceplatform.models;

public class JobHistory {
	
	private Bid bid;
	
	private Feedback feedback;
	
	private Job job;

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
			
}
