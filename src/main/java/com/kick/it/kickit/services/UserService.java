package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.Customer;
import com.kick.it.kickit.entities.Role;

import java.util.Set;

public interface UserService {


    public Customer createUser(Customer customer) throws Exception;

    public Customer getUser(String username);

    public Customer updateUser(Customer customer);

    public void deleteUser(String username);
}
