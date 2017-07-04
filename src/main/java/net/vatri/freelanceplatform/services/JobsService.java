package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.repositories.JobRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {

    @Autowired
    JobRepository jobRepository;

//    @Autowired
//    private SessionFactory sessionFactory;

    public Job get(long id){
        return jobRepository.findOne(id);
    }

    public Job add(Job job){
        return jobRepository.save(job);
    }

    public List<Job> list(){
        return jobRepository.findAll();
    }

}
