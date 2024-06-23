package admin_user.service;

import admin_user.Dto.RegisterDto;
import admin_user.model.Customer;
import admin_user.model.User;
import admin_user.repositories.CustomerRepository;
import admin_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public User saveUser(RegisterDto registerDto) {
        User user = new User(registerDto.getEmail(),registerDto.getPassword(), 1,true);
        return userRepository.save(user);
    }


    @Override
    public void saveUserAndCustomer(RegisterDto registerDto) {
        User user = saveUser(registerDto);
        Customer customer = new Customer(user.getUserId(), registerDto.getFullname(), registerDto.getAddress(), registerDto.getPhone());
        customerRepository.save(customer);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
