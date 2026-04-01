package com.codefromscratch.ticket;

import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String service;
    private String id_applicant;
    private String id_technician;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Ticket(String id, String title, String description, String status, String priority, String service, String id_applicant, String id_technician) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.service = service;
        this.id_applicant = id_applicant;
        this.id_technician = id_technician;
        this.created_at = LocalDateTime.now();
    }
}
