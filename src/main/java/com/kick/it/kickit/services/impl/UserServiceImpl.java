package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Customer;
import com.kick.it.kickit.entities.Role;
import com.kick.it.kickit.repository.CustomerRepository;
import com.kick.it.kickit.repository.RoleRepo;
import com.kick.it.kickit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Customer createUser(Customer customer) throws Exception {
            Customer local = customerRepository.findByUsername(customer.getUsername());
            if(local!=null){
                throw new Exception("User already exist");
            }else{
                //converting plan password into hash password
                String hashPassword=this.passwordEncoder.encode(customer.getPassword());
                customer.setPassword(hashPassword);
                customer.setCreatedAt(new Date(System.currentTimeMillis()));
                local = customerRepository.save(customer);
             }
        return local;
    }

    @Override
    public Customer getUser(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer updateUser(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteUser(String username) {
        customerRepository.delete(getUser(username));
    }
}
