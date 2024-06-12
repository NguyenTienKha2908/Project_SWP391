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
        return userRepository.findAllUsers();
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public List<User> getUsersByRoleId(int roleId) {
        return userRepository.findUsersByRoleId(roleId);
    }

    public User getUsersByUserId(int user_Id) {
        return userRepository.findUsersByUserId(user_Id);
    }

}
