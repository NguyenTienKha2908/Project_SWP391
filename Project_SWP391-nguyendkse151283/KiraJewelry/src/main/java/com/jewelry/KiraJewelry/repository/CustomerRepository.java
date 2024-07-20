package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jewelry.KiraJewelry.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c.fullName FROM Customer c WHERE c.user.userId = :userId")
    String findCustomerNameByUserId(@Param("userId") int userId);

    @Query("SELECT c FROM Customer c WHERE c.user.userId = :userId")
    Customer findByUserId(@Param("userId") int userId);
}