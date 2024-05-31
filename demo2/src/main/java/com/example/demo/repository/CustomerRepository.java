package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c.full_Name FROM Customer c WHERE c.user.user_Id = :user_Id")
    String findCustomerNameByUserId(@Param("user_Id") int user_Id);

    @Query("SELECT c FROM Customer c WHERE c.user.user_Id = :user_Id")
    Customer findByUserId(@Param("user_Id") int user_Id);
=======

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    // Add custom methods if needed
>>>>>>> 61c9e9129b22293c5ceba01d13895817a0aa0aca
}