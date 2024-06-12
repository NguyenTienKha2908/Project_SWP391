package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c.full_Name FROM Customer c WHERE c.user.user_Id = :user_Id")
    String findCustomerNameByUserId(@Param("user_Id") int user_Id);

    @Query("SELECT c FROM Customer c WHERE c.full_Name = :full_Name")
    Customer findCustomerIdByCustomerName(@Param("full_Name") String full_Name);

    @Query("SELECT c FROM Customer c WHERE c.user.user_Id = :user_Id")
    Customer findByUserId(@Param("user_Id") int user_Id);

    @Query("SELECT c FROM Customer c WHERE c.customer_Id = :customer_Id")
    Customer findByCustomerId(@Param("customer_Id") String customer_Id);
}