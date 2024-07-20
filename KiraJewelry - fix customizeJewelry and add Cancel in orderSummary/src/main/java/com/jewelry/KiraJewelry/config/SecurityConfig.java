package com.jewelry.KiraJewelry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jewelry.KiraJewelry.service.CustomSuccessHandler;
import com.jewelry.KiraJewelry.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeRequests(request -> request
                .requestMatchers("/", "/registration", "/css/**").permitAll()
                .requestMatchers("/").hasAuthority("1")
                .requestMatchers("employee/manager/**").hasAuthority("3")
                .requestMatchers("employee/sale_staff/profile").hasAuthority("4")
                .requestMatchers("employee/design_staff/home").hasAuthority("5")
                .requestMatchers("employee/production_staff/home").hasAuthority("6")
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler)
                        .permitAll())
                .logout(form -> form
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }
}
// @Autowired
// public void configure(AuthenticationManagerBuilder auth) throws Exception {
// auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//
// }
