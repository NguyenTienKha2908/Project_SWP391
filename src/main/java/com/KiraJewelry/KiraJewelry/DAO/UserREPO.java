package com.KiraJewelry.KiraJewelry.DAO;

import com.KiraJewelry.KiraJewelry.DTO.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserREPO extends JpaRepository<UserDTO, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (email, password, roleId, status) VALUES (:email, :password, :roleId, :status)", nativeQuery = true)
    void saveUser(@Param("email") String email, @Param("password") String password, @Param("roleId") int roleId, @Param("status") int status);
}
