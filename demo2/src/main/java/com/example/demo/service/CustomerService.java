package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public String getCustomerNameByUserId(int user_Id) {
        return customerRepository.findCustomerNameByUserId(user_Id);
    }

    public Customer getCustomerByUserId(int user_Id) {
        return customerRepository.findByUserId(user_Id);
    }
}
