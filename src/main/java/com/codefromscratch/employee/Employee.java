package com.codefromscratch.employee;

import java.util.UUID;

public abstract class Employee {
    protected String id;
    protected final String name;
    protected final Service service;

    public Employee(String name, Service service) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.service = service;
    }

    public abstract String listAllTickets();
}
