package com.jewelry.KiraJewelry.dto;

import io.micrometer.core.instrument.config.validate.Validated.Invalid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid Email")
    private String email;
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,64})", message="Invalid Password: Password must be 8 - 6 characters, 1 special character, 1 digit, and 1 uppercase")
    private String password;

    @NotNull
    private int role = 1; // Default role to 1
    private boolean status = true; // Default status to true
    @Pattern(regexp = "^[^\\d]*$", message = "Invalid Full Name: Full Name cannot contain numbers!")    private String fullname;
    private String address;
    
    @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid Phone Number: Must start with 0 and have 10 digits")
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
