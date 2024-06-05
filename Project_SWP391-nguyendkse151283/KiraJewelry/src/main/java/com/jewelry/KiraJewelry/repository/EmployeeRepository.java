package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jewelry.KiraJewelry.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.user.id = :userId")
    Employee findEmployeeByUserId(@Param("userId") int userId);

    @Query("SELECT e FROM Employee e WHERE e.id = :employeeId")
    Employee findEmployeeById(@Param("employeeId") String employeeId);
}

