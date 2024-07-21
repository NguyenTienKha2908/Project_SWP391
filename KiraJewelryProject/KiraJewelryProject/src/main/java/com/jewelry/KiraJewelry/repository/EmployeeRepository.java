package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.user.user_Id = :user_Id")
    Employee findEmployeeByUserId(@Param("user_Id") int user_Id);

    @Query("SELECT e FROM Employee e WHERE e.employee_Id = :employee_Id")
    Employee findEmployeeById(@Param("employee_Id") String employee_Id);
}
