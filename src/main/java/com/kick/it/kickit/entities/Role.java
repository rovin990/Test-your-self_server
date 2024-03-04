package com.kick.it.kickit.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")

public class Role {
    @Id
    @Column(name = "id")
    private int rId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Role() {
    }

    public Role(String name, Customer customer) {
        this.name = name;
        this.customer = customer;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
