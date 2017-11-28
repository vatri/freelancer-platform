package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User get(Long id){
        return userRepository.findOne(id);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User user){
		user.setPassword( passwordEncoder.encode( user.getPassword() )  );
        return userRepository.save(user);
    }

}
