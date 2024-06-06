package com.KiraJewelry.KiraJewelry.DTO;

import jakarta.persistence.*;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Entity
@Table(name = "Customer")
public class CustomerDTO {
    // Counter to keep track of the last customer ID
    private static final AtomicInteger counter = new AtomicInteger(1);

    @Id
    @Column(name = "CustomerId")
    private String customerId;
    private String fullName;
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    private String address;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDTO user;


}
