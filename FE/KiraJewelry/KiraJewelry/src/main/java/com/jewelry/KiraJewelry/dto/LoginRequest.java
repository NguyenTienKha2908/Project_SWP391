package com.jewelry.KiraJewelry.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;

    @NotNull
    private int role = 1; // Default role to 1
    private boolean status = true; // Default status to true
    private String fullname;
    private String address;
    private String phone;

    public LoginRequest() {
        // Default constructor
    }

    public LoginRequest(String email, String password, int role, boolean status, String fullname, String address,
            String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

}
