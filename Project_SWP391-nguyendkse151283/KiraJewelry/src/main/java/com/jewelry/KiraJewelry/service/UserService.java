package com.jewelry.KiraJewelry.service;

import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRoleId(int roleId) {
        return userRepository.findUsersByRoleId(roleId);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
