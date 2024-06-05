package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeByUserId(int userId) {
        return employeeRepository.findEmployeeByUserId(userId);
    }

    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findEmployeeById(employeeId);
    }
}
