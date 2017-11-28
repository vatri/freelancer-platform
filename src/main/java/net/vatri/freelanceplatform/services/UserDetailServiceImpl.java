package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.repositories.UserRepository;
import net.vatri.freelanceplatform.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private Cache cache;
//
//    @Autowired
//    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = (User)cache.getItem("user/"+username, User.class);
//        if( user == null){
//            user = userRepository.findByEmail(username);
//        }
//
//        if( user == null){
//            throw new UsernameNotFoundException("No user found. Username tried: " + username);
//        }
//
//        cache.setItem("user/"+username, user);
    	
    	User user = userRepository.findByEmail(username);

        if( user == null){
        	throw new UsernameNotFoundException("No user found with this username: " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("user"));

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getEmail(), user.getPassword(), grantedAuthorities);
        userDetails.setFullName(user.getName());
        return userDetails;
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
