package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM Users", nativeQuery = true)
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.role_Id = :role_Id")
    List<User> findUsersByRoleId(@Param("role_Id") int role_Id);

    @Query("SELECT u FROM User u WHERE u.user_Id = :user_Id")
    User findUsersByUserId(@Param("user_Id") int user_Id);

    User findByEmail (String email);
}
