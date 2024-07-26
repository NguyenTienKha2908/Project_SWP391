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
                        .requestMatchers("/", "/registration", "/css/**", "/BlogList", "/education",
                                "/viewCustomerMaterialPriceListPage", "/viewCustomerMaterialsPage",
                                "/viewCustomerDiaPriceListPage", "/customerDiamondsPage", "/customerViewCollection", "/request")
                        .permitAll()
                        .requestMatchers("/customizeJewelry", "/request", "/viewCollection").hasAuthority("1")
                        .requestMatchers("employee/manager/profile",
                        "employee/manager/Blog/blogs",
                        "employee/manager/Blog/new_blog",
                        "employee/manager/Blog/update_blog",

                        "employee/manager/Category/categories",
                        "employee/manager/Category/new_category",
                        "employee/manager/Category/update_category",

                        "employee/manager/Collection/collections",
                        "employee/manager/Collection/new_collection",
                        "employee/manager/Collection/update_collection",

                        "employee/manager/Diamond/diamonds",
                        "employee/manager/Diamond/new_diamond",
                        "employee/manager/Diamond/update_diamond",

                        "employee/manager/DiamondPriceList/dia_price_lists",
                        "employee/manager/DiamondPriceList/new_dia_price_list",
                        "employee/manager/DiamondPriceList/update_dia_price_list",

                        "employee/manager/Material/materials",
                        "employee/manager/Material/new_material",
                        "employee/manager/Material/update_material",

                        "employee/manager/MaterialPriceList/material_price_lists",
                        "employee/manager/MaterialPriceList/new_material_price_list",
                        "employee/manager/MaterialPriceList/update_material_price_list",
                        
                        "employee/manager/viewRequest",
                        "employee/manager/viewQuote",
                        "employee/manager/viewOrder",
                        "employee/manager/viewInForRequest",
                        "employee/manager/viewInForQuote",
                        "employee/manager/viewInForOrder",

                        "employee/manager/managerDashboard",

                        "employee/manager/Users/add_staff",
                        "employee/manager/Users/manage_staff",
                        "employee/manager/Users/update_staff",
                        "employee/manager/Users/manage_users",

                        "employee/editProfile").hasAuthority("3")
                        .requestMatchers("employee/sale_staff/profile",
                        "employee/sale_staff/viewRequest",
                        "employee/sale_staff/viewQuote",
                        "employee/sale_staff/viewOrder",
                        "employee/sale_staff/viewInForRequest",
                        "employee/sale_staff/viewInForQuote",
                        "employee/sale_staff/viewInForOrder",
                        "employee/sale_staff/previewQuotePage",
                        "employee/sale_staff/findIngredientsPage",
                        "employee/editProfile").hasAuthority("4")
                        .requestMatchers("employee/design_staff/profile", 
                        "employee/design_staff/viewAllDesign", 
                        "employee/design_staff/viewInForRequest",
                        "employee/design_staff/viewRequest",
                        "employee/editProfile").hasAuthority("5")
                        .requestMatchers("employee/production_staff/profile",
                        "employee/production_staff/viewAllProgressPhoto", 
                        "employee/production_staff/viewInForRequest",
                        "employee/production_staff/viewRequest", 
                        "employee/editProfile").hasAuthority("6")
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
