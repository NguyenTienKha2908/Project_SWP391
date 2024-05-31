package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e.full_Name FROM Employee e WHERE e.user.user_Id = :user_Id")
    String findEmployeeNameByUserId(@Param("user_Id") int user_Id);
}
