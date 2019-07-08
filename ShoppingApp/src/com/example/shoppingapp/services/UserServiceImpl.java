package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.repository.UserRepository;
import com.example.shoppingapp.repository.UserRepositoryImpl;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public boolean isAdmin(User tempUser) throws DataException {
        if (isUser(tempUser)) {
            User user = getUser(tempUser.getUsername());

            if (user.getRole().equals("admin")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User getUser(String username) throws DataException {
        return userRepository.getUser(username);
    }

    @Override
    public boolean isUser(User tempUser) throws DataException {
        boolean loggedInState = false;
        User user = userRepository.getUser(tempUser.getUsername());

        if (tempUser.getUsername() != null) {
            if (user.getUsername().equals(tempUser.getUsername())
                    && user.getPassword().equals(tempUser.getPassword())) {
                loggedInState = true;
            }
        }

        return loggedInState;
    }

    @Override
    public List<User> getUserList() throws DataException {
        return userRepository.getUserList();
    }
}

