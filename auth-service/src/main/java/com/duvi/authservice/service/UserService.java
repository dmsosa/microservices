package com.duvi.authservice.service;

import com.duvi.authservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public User findUserByUsername(String username);
    public User findByEmail(String email);
    public User findByLogin(String login);
    public void saveUser(User user) throws Exception;
    public User updateUser(String oldUserId, User updatedUser);
    public void deleteUserById(Long id);

}
