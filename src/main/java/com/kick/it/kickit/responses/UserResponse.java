package com.kick.it.kickit.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;



public class UserResponse {
    @JsonIgnore
    private String massage;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private Set<String> roles;
    @JsonIgnore
    private String mobile;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String name;

    public UserResponse() {
    }

    public UserResponse(String massage, String status, String username, Set<String> roles, String mobile, String email, String name) {
        this.massage = massage;
        this.status = status;
        this.username = username;
        this.roles = roles;
        this.mobile = mobile;
        this.email = email;
        this.name = name;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
