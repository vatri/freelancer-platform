package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Job;
import net.vatri.freelanceplatform.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {

    @Autowired
    JobRepository jobRepository;

    public Job get(long id){
        return jobRepository.findOne(id);
    }

    public Job add(Job job){

        job.setType( job.getType().toLowerCase() );
        job.setExpertizeLevel( job.getExpertizeLevel().toLowerCase() );

        return jobRepository.save(job);
    }

    public List<Job> list(){

        List<Job> result = jobRepository.findAll();

        result.sort( (j1,j2) -> {
            return j1.getId() > j2.getId() ? -1 : 0;
        } );

        return result;
    }

}
