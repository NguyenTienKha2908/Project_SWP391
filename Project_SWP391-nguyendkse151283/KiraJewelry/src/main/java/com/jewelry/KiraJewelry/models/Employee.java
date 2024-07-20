package com.jewelry.KiraJewelry.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "Employee_Id")
    private String id;

    @Column(name = "Full_Name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;
}
