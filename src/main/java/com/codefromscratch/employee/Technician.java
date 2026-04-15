package com.codefromscratch.employee;

public class Technician extends Employee {
    public Technician(String name) {
        super(name, Service.SUPPORT);
    }

    @Override
    public String listAllTickets() {
        return "";
    }
}
