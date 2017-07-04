package net.vatri.freelanceplatform.models;

import javax.persistence.*;
import java.util.List;

//enum JobType{ FIXED, HOURLY }
//enum ExpertizeLevel{ BEGINNER = "beginner", INTERMEDIATE, EXPERT }

@Entity
public class Job {
    private long id;
    private String title;
    private String description;
    private double budget;
    private String type;
    private String expertizeLevel;
    private String created;
//    private List interviewQuestions;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpertizeLevel() {
        return expertizeLevel;
    }

    public void setExpertizeLevel(String expertizeLevel) {
        this.expertizeLevel = expertizeLevel;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

//    public List getInterviewQuestions() {
//        return interviewQuestions;
//    }
//
//    public void setInterviewQuestions(List interviewQuestions) {
//        this.interviewQuestions = interviewQuestions;
//    }
}
