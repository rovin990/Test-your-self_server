package com.kick.it.kickit.config;

import com.kick.it.kickit.entities.Customer;
import com.kick.it.kickit.entities.Role;
import com.kick.it.kickit.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class QuizUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username= authentication.getName();
        String password=authentication.getCredentials().toString();

        Customer local = customerRepository.findByUsername(username);

        if(local!=null){
            if(passwordEncoder.matches(password, local.getPassword())){
                return  new UsernamePasswordAuthenticationToken(username,password,generateUserRole(local.getRoles()));
            }
            else{
                throw new BadCredentialsException("Invalid password");
            }
        }
        else{
            throw  new BadCredentialsException("No valid user present");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public List<GrantedAuthority> generateUserRole(Set<Role> roles){
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();

        for(Role role:roles){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
        return grantedAuthorities;
    }
}
