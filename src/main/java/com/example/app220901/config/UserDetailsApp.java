package com.example.app220901.config;

import com.example.app220901.model.Customer;
import com.example.app220901.model.SecurityCustomer;
import com.example.app220901.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsApp implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customer = customerRepository.findByEmail(username);
        if(customer.size() ==0){
            throw new UsernameNotFoundException("user details not found for the user:" + username);

        }
        return new SecurityCustomer(customer.get(0));
    }
}
