package com.codefromscratch.employee;

public class Technician extends Employee {
    public Technician(String name) {
        super(name, Service.SUPPORT);
    }

    public String listAllTickets() {
        return "";
    }
}
