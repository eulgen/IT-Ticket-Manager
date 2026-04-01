package com.codefromscratch.employee;

public class Employee {
    protected String id;
    protected String name;
    protected String service;
    protected Rule rule=Rule.APPLICANT;

    public Employee(String id, String name, String service, Rule rule) {
        this.id = id;
        this.name = name;
        this.service = service;
        this.rule = rule;
    }

    public Employee(String id, String name, String service) {
        this(id, name, service, Rule.APPLICANT);
    }

    public void findTicket(){

    }

    public void listAllTickets(){

    }

    public void ticketFilter(){

    }
}
