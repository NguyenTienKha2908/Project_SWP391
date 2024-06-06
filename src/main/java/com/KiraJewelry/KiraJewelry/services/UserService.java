package com.KiraJewelry.KiraJewelry.services;

import com.KiraJewelry.KiraJewelry.DTO.CustomerDTO;
import com.KiraJewelry.KiraJewelry.DTO.UserDTO;

public interface UserService {
//    void registerUser(UserDTO user);
//
//    void registerCustomer(CustomerDTO customer);

    void registerUserAndCustomer(UserDTO user, CustomerDTO customer);
}
