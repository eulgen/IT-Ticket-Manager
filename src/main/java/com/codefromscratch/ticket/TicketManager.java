package com.codefromscratch.ticket;

import com.codefromscratch.employee.Service;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class TicketManager {

    private final Set<Ticket> tickets;

    public TicketManager(){
        this.tickets = new HashSet<>();
    }

    public Ticket createTicket(String title, String description, Priority priority, Service service, String name_applicant, String name_technician){
        Ticket ticket =  new Ticket(title, description, Status.OPEN,priority, service, name_applicant, name_technician);
        tickets.add(ticket);
        return ticket;
    }

    public void updateTicket(String id,Priority priority) {
        Ticket findedTicket = findTicketById(id);
        findedTicket.setPriority(priority);
        findedTicket.setUpdated_at(LocalDateTime.now());
        deleteTicket(id);
        tickets.add(findedTicket);
    }

    public void updateTicket(String id, String technician){
        Ticket findedTicket = findTicketById(id);
        findedTicket.setName_technician(technician);
        findedTicket.setUpdated_at(LocalDateTime.now());
        deleteTicket(id);
        tickets.add(findedTicket);
    }

    public Set<Ticket> deleteTicket(String ticketId){
        Ticket findedTicket = findTicketById(ticketId);
        tickets.remove(findedTicket);
        return tickets;
    }

    public void changeStatus(){
    }

    public String listAllTickets(){
        return tickets.stream()
                .map(ticket ->ticket.getId() + " - " + ticket.getTitle() + " - " + ticket.getDescription() + " - "
                        + ticket.getStatus() + " - " + ticket.getPriority() + " - " + ticket.getService()
                        + " - " + ticket.getName_applicant() + " - " + ticket.getName_technician()
                        + " - " + ticket.getCreated_at() + " - " + ticket.getUpdated_at()
                )
                .collect(Collectors.joining("\n"));
    }

    public Set<Ticket> filterTicketbyTitle(String name){
        return tickets.stream()
                .filter(ticket->ticket.getTitle().contains(name))
                .collect(Collectors.toSet());
    }

    public Set<Ticket> filterTicketByStatus(String status){
        return tickets.stream()
                .filter(ticket -> ticket.getStatus().toString().contains(status))
                .collect(Collectors.toSet());
    }

    public Set<Ticket> filterTicketByPriority(String priority){
        return tickets.stream()
                .filter(ticket -> ticket.getPriority().toString().contains(priority))
                .collect(Collectors.toSet());
    }

    public Set<Ticket> filterTicketByService(String service){
        return tickets.stream()
                .filter(ticket -> ticket.getService().toString().contains(service))
                .collect(Collectors.toSet());
    }

    public Set<Ticket> filterTicketByTechnician(String technician){
        return tickets.stream()
                .filter(ticket -> ticket.getName_technician().contains(technician))
                .collect(Collectors.toSet());
    }

    public Set<Ticket> filterTicketByApplicant(String applicant){
        return tickets.stream()
                .filter(ticket -> ticket.getName_applicant().contains(applicant))
                .collect(Collectors.toSet());
    }

    public Ticket findTicketById(String id){
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst().orElseThrow();
    }

    public Ticket findTicketByTitle(String title){
        return tickets.stream()
                .filter(ticket -> ticket.getTitle().equals(title))
                .findFirst().orElseThrow();
    }

}


