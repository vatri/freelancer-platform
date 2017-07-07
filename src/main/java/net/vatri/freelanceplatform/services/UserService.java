package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
