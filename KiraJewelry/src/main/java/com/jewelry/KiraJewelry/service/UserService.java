package com.jewelry.KiraJewelry.service;

import com.jewelry.KiraJewelry.dto.LoginRequest;
import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.repository.CustomerRepository;
import com.jewelry.KiraJewelry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public User getUsersById(int id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }


    public void saveUsers(User users) {
        userRepository.save(users);
    }

    public User saveUser(LoginRequest registerDto) {
        User user = new User(registerDto.getEmail(),registerDto.getPassword(), 1,true);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUserAndCustomer(LoginRequest registerDto) {
        User user = saveUser(registerDto);
        Customer customer = new Customer(user, registerDto.getFullname(), registerDto.getAddress(), registerDto.getPhone());
        customerRepository.save(customer);
    }

    public void activateUsers(int id) {
        User users = getUsersById(id);
        if (users != null) {
            users.setStatus(true);
            saveUsers(users);
        }
    }

    public void deactivateUsers(int id) {
        User users = getUsersById(id);
        if (users != null) {
            users.setStatus(false);
            saveUsers(users);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
