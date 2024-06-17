package admin_user.service;

import admin_user.Dto.RegisterDto;
import admin_user.model.Customer;
import admin_user.model.User;



public interface UserService {
    User saveUser(RegisterDto registerDto);
    void saveUserAndCustomer(RegisterDto registerDto);
    User findByEmail(String email);

}
