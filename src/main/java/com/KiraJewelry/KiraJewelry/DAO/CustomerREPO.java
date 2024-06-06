package com.KiraJewelry.KiraJewelry.DAO;

import com.KiraJewelry.KiraJewelry.DTO.CustomerDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerREPO extends JpaRepository<CustomerDTO, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Customer (CustomerId, FullName, Address, PhoneNumber, UserId) VALUES (:customerId, :fullname, :address, :phoneNumber, :userId)", nativeQuery = true)
    void saveCustomer(@Param("customerId") String customerId,
                      @Param("fullname") String fullname,
                      @Param("address") String address,
                      @Param("phoneNumber") String phoneNumber,
                      @Param("userId") Integer userId);
}
