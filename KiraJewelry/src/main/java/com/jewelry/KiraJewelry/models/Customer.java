package com.jewelry.KiraJewelry.models;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "customer-id-generator")
    @GenericGenerator(name = "customer-id-generator", strategy = "com.jewelry.KiraJewelry.models.CustomerIdGenerator")
    @Column(name = "Customer_Id", length = 10)
    private String customer_Id;
    
    @Column(nullable = false, name = "Full_Name")
    private String full_Name;

    @Column(nullable = false, name = "Address")
    private String address;

    @Column(nullable = false, name = "PhoneNumber")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;

    public Customer(User user, String full_Name, String address, String phoneNumber) {
        this.user = user;
        this.full_Name = full_Name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
