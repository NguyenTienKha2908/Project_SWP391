package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "EmployeeId")
    private String employeeId;
    
    @Column(name = "Full_Name")
    private String full_Name;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;
}
