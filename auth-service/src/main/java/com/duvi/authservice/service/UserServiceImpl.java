package com.duvi.authservice.service;

import com.duvi.authservice.model.User;
import com.duvi.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Zu Machen: EXCEPTIONS
    @Override
    public User findUserByUsername(String username) {
        User user = (User) userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }
        return optionalUser.get();
    }

    @Override
    public User findByLogin(String login) {
        User user = this.findUserByUsername(login);
        if (user == null){
            Optional<User> optUser = userRepository.findByEmail(login);
            return optUser.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("exists by Username");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("exists by Email");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
        logger.info("New user has been registered: {}!".formatted(newUser.getUsername()));
    }

    @Override
    public User updateUser(String oldUserId, User updatedUser) {
        Optional<User> oldUser = userRepository.findById(oldUserId);
        if (oldUser.isEmpty()) {
            User newUser = userRepository.save(updatedUser);
            return newUser;
        }
        oldUser.get().updateWithUser(updatedUser);
        User newUser = userRepository.save(oldUser.get());
        return newUser;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
