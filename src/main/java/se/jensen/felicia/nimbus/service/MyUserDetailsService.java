package se.jensen.felicia.nimbus.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.felicia.nimbus.model.User;
import se.jensen.felicia.nimbus.repository.UserRepository;
import se.jensen.felicia.nimbus.security.MyUserDetails;
@Service
public class MyUserDetailsService implements UserDetailsService {

        private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new MyUserDetails(user);
    }
}
