package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public String getCustomerNameByUserId(int userId) {
        return customerRepository.findCustomerNameByUserId(userId);
    }

    public Customer getCustomerByUserId(int userId) {
        return customerRepository.findByUserId(userId);
    }
}

