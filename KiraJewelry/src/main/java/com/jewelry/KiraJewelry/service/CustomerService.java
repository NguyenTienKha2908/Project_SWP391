package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.repository.CustomerRepository;

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

    public Customer getCustomerByCustomerId(String customer_Id) {
        return customerRepository.findByCustomerId(customer_Id);
    }
}
