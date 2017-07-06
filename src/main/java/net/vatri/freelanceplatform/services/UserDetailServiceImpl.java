package net.vatri.freelanceplatform.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.freelanceplatform.cache.Cache;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.repositories.UserRepository;
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

    @Autowired
    private Cache cache;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

System.out.println("Logging in. Username: " + username);

        User user = (User)cache.getItem("user/"+username, User.class);
System.out.println(" 2 ");
        if( user == null){
            user = userRepository.findByEmail(username);
System.out.println(" 3 ");
        }

        if( user == null){
System.out.println(" 4 ");
            throw new UsernameNotFoundException("No user found. Username tried: " + username);
        }
System.out.println(" 5 ");

        cache.setItem("user/"+username, user);
System.out.println(" 6 ");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
System.out.println(" 7 ");
        grantedAuthorities.add(new SimpleGrantedAuthority("user"));
System.out.println(" 8 ");


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
