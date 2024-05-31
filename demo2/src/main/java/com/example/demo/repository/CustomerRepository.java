package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {


    @Query("SELECT c.full_Name FROM Customer c WHERE c.user.user_Id = :user_Id")
    String findCustomerNameByUserId(@Param("user_Id") int user_Id);

    @Query("SELECT c FROM Customer c WHERE c.user.user_Id = :user_Id")
    Customer findByUserId(@Param("user_Id") int user_Id);


}