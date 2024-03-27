package com.duvi.authservice.service;

import com.duvi.authservice.model.User;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.model.exception.UserNotExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public User findUserByUsername(String username) throws UserNotExistsException;
    public User findByEmail(String email) throws UserNotExistsException;
    public User findByLogin(String login) throws UserNotExistsException;
    public void saveUser(User user) throws UserExistsException;
    public User updateUser(String oldUserId, User updatedUser);
    public void deleteUser(String username) throws UserNotExistsException;

}
