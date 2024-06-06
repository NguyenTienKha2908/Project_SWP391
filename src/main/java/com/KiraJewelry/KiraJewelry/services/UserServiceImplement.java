package com.KiraJewelry.KiraJewelry.services;

import com.KiraJewelry.KiraJewelry.DAO.CustomerREPO;
import com.KiraJewelry.KiraJewelry.DAO.UserREPO;
import com.KiraJewelry.KiraJewelry.DTO.CustomerDTO;
import com.KiraJewelry.KiraJewelry.DTO.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserREPO userRepository;

    @Autowired
    private CustomerREPO customerRepository;

//    @Override
//    @Transactional
//    public void registerUser(UserDTO user) {
//        // Set default values for roleId and status
//        user.setRoleId(1);
//        user.setStatus(1);
//        // Save the user
//        userRepository.save(user);
//    }
//
//    @Override
//    @Transactional
//    public void registerCustomer(CustomerDTO customer) {
//        // Set default value for membership
//        customer.setMembership("silver");
//        // Save the customer
//        customerRepository.save(customer);
//    }

    @Override
    @Transactional
    public void registerUserAndCustomer(UserDTO user, CustomerDTO customer) {
        // Assuming user is already saved and has an ID
        userRepository.saveUser(user.getEmail(), user.getPassword(), 1, 1);
        customer.setUser(user);
        customerRepository.saveCustomer(customer.getCustomerId(), customer.getFullName(), customer.getAddress(), customer.getPhoneNumber(), user.getUserId());

    }
}
