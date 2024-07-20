package com.jewelry.KiraJewelry.models;

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
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "employee_id")
    private String employee_Id;
    
    @Column(name = "full_name")
    private String full_Name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
