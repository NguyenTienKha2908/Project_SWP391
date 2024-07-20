package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users getUsersById(int id) {
        Optional<Users> optional = usersRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveUsers(Users users) {
        usersRepository.save(users);
    }

    @Override
    public void activateUsers(int id) {
        Users users = getUsersById(id);
        if (users != null) {
            users.setStatus(true);
            saveUsers(users);
        }
    }

    @Override
    public void deactivateUsers(int id) {
        Users users = getUsersById(id);
        if (users != null) {
            users.setStatus(false);
            saveUsers(users);
        }
    }
}
