package com.codefromscratch.ticket;

import com.codefromscratch.employee.Service;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Ticket {
    private final String id;
    private final String title;
    private final String description;
    private Status status;
    private Priority priority;
    private final Service service;
    private final String name_applicant;
    private String name_technician = "NOT ASSIGN";
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Ticket(String title, String description, Status status, Priority priority, Service service, String name_applicant, String name_technician) {
        this.id = generateId();

        if(title == null || title.isBlank()) throw new InvalidParameterException("The ticket title must be filled in");
        if(description == null || description.isBlank()) throw new InvalidParameterException("The ticket description must be filled in");
        if(service == null) throw new InvalidParameterException("The ticket service must be filled in");
        if(name_applicant == null || name_applicant.isBlank()) throw new InvalidParameterException("The ticket applicant name must be filled in");
        if(priority == null) throw new InvalidParameterException("The ticket priority must be filled in");

        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.service = service;
        this.name_applicant = name_applicant;
        this.name_technician = name_technician != null ? name_technician : "NOT ASSIGN";
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public Ticket(String id,String title, String description, Status status, Priority priority, Service service, String name_applicant,String name_technician,LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.service = service;
        this.name_applicant = name_applicant;
        this.name_technician = name_technician;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Ticket(String title, String description, Status status, Priority priority, Service service, String name_applicant) {
        this(title, description, status, priority, service, name_applicant, "NOT ASSIGN");
    }

    public String generateId() {
        return "Tick-EG-"+UUID.randomUUID().toString().substring(0, 6);
    }

    public void assignTechnician(String name_technician){
        this.name_technician = name_technician;
    }

    public void changeStatus(Status status){
        switch (status){
            case OPEN:
                this.status = Status.IN_PROGRESS;
                break;
            case IN_PROGRESS:
                this.status = Status.RESOLVED;
                break;
            case RESOLVED:
                this.status = Status.CLOSED;
                break;
            default:
        }
    }

    public void changeStatus(){
        switch (this.status){
            case OPEN:
                this.status = Status.IN_PROGRESS;
                break;
            case IN_PROGRESS:
                this.status = Status.RESOLVED;
                break;
            case RESOLVED:
                this.status = Status.CLOSED;
                break;
            default:
        }
    }

    public void changePriority(Priority priority){
        this.priority = priority;
    }

    public void descriptionTicket(){
        System.out.println("<----------------------Ticket Description---------------------->");
        System.out.println("                        ID: " + this.id);
        System.out.println("                        Title: " + this.title);
        System.out.println("                        Description: " + this.description);
        System.out.println("                        Status: " + this.status);
        System.out.println("                        Priority: " + this.priority);
        System.out.println("                        Service: " + this.service);
        System.out.println("                        Name Applicant: " + this.name_applicant);
        System.out.println("                        Name Technician: " + this.name_technician);
        System.out.println("                        Created At: " + this.created_at);
        System.out.println("                        Updated At: " + this.updated_at);
    }
}
