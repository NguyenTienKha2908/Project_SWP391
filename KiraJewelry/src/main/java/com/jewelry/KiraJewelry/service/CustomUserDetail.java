package com.jewelry.KiraJewelry.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private User user;
    private Customer customer;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->  String.valueOf(user.getRole_Id()));
    }

    public String getFullName(){
        return customer.getFull_Name();
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
