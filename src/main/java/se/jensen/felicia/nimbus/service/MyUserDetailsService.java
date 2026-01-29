package se.jensen.felicia.nimbus.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.felicia.nimbus.model.User;
import se.jensen.felicia.nimbus.repository.UserRepository;
import se.jensen.felicia.nimbus.security.MyUserDetails;
@Service
public class MyUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

    public MyUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            System.out.println("LOGG: Försöker logga in användare: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("LOGG: Hittade inte användare i databasen!");
                    return new UsernameNotFoundException("User not found: " + username);
                });
            System.out.println("LOGG: Hittade användare! Hash i DB: " + user.getPassword());
        return new MyUserDetails(user);
    }
}
