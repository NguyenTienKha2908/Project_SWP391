package com.jewelry.KiraJewelry.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_Id")
    private String customerId;

    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone_Number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "User_Id", referencedColumnName = "User_Id")
    private User user;
}

