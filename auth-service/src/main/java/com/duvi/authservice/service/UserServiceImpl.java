package com.duvi.authservice.service;

import com.duvi.authservice.model.User;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.model.exception.UserNotExistsException;
import com.duvi.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) throws UserNotExistsException {
        User user = (User) userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotExistsException("The user with username: \"%s\" does not exist!".formatted(username));
        }
        return user;
    }

    @Override
    public User findByEmail(String email) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotExistsException("The user with email: \"%s\" does not exist!".formatted(email));
        }
        return optionalUser.get();
    }

    @Override
    public User findByLogin(String login) throws UserNotExistsException {
        User user = this.findUserByUsername(login);
        if (user == null){
            Optional<User> optUser = userRepository.findByEmail(login);
            return optUser.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) throws UserExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException("The username: \"%s\" is already in use".formatted(user.getUsername()));
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException("The email: \"%s\" is already in use".formatted(user.getEmail()));
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        newUser.setPassword(encryptedPassword);
        userRepository.save(newUser);
        logger.info("New user has been registered: %s!".formatted(newUser.getUsername()));
    }

    @Override
    public User updateUser(String oldUsername, User updatedUser) {
        User oldUser = (User) userRepository.findByUsername(oldUsername);
        if (oldUser == null) {
            return userRepository.save(updatedUser);
        }
        oldUser.updateWithUser(updatedUser);
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(String username) throws UserNotExistsException {
        User user = (User) userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotExistsException("The user with username: \"%s\" does not exist!".formatted(username));
        }
        userRepository.delete(user);
    }
}
