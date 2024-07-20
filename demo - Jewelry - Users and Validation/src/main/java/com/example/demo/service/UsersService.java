package com.example.demo.service;

import com.example.demo.entity.Users;
import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();
    Users getUsersById(int id);
    void saveUsers(Users users);
    void activateUsers(int id);
    void deactivateUsers(int id);
}
