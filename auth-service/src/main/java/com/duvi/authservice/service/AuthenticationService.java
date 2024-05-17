package com.duvi.authservice.service;

import com.duvi.authservice.model.User;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @PostConstruct
    public void saveDemoUser() throws UserExistsException {
        String encodedPassword = encoder.encode("password");
        User demoUser = new User(0L, "demo", "demo@demo.com", encodedPassword);
        userRepository.save(demoUser);
    }
}
