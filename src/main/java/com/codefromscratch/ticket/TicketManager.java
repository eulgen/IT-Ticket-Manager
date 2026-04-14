package com.codefromscratch.ticket;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class TicketManager {

    private final TicketRepo tickets;

    public TicketManager(){
        this.tickets = new TempTicketRepo();
    }

    public TicketManager(TicketRepo ticketsRepo){this.tickets = ticketsRepo;}

    public void createTicket(String title, String description, Priority priority, Service service, String name_applicant, String name_technician){
        Ticket ticket =  new Ticket(title.toLowerCase(), description.toLowerCase(), Status.OPEN,priority, service, name_applicant.toLowerCase(), name_technician.toLowerCase());
        tickets.saveData(ticket);
    }

    public void saveData(Ticket ticket){
        tickets.saveData(ticket);
    }

    public void saveDatas(Set<Ticket> mytickets){
        tickets.saveDatas(mytickets);
    }

    public Set<Ticket> loadDatas(){
        return tickets.loadDatas();
    }

    public void deleteAll(){
        tickets.deleteDatas();
    }

    public Ticket findTicketById(String id){
        return tickets.findTicketById(id);
    }

    public Ticket findTicketByTitle(String title){
        return tickets.findTicketByTitle(title);
    }

    public void updateTicketByPriority(String id,String priority) {
        tickets.updateTicketByPriority(id,priority);
    }

    public void updateTicketByTechnician(String id, String technician){
        tickets.updateTicketByTechnician(id,technician);
    }

    public void updateTicketByStatus(String id){
        tickets.updateTicketByStatus(id);
    }

    public void deleteTicket(String ticketId){
        tickets.deleteTicket(ticketId);
    }

    public String listAllTickets(){
        Set<Ticket> mytickets = tickets.loadDatas();
        return mytickets.stream()
                .map(ticket ->ticket.getId() + " - " + ticket.getTitle() + " - " + ticket.getDescription() + " - "
                        + ticket.getStatus() + " - " + ticket.getPriority() + " - " + ticket.getService()
                        + " - " + ticket.getName_applicant() + " - " + ticket.getName_technician()
                        + " - " + ticket.getFormattedDateTime(ticket.getCreated_at()) + " - " + ticket.getFormattedDateTime(ticket.getUpdated_at())
                )
                .collect(Collectors.joining("\n"));
    }

    public Set<Ticket> filterTicketbyTitle(String name){
        return tickets.filterTicketbyTitle(name);
    }

    public Set<Ticket> filterTicketByStatus(String status){
        return tickets.filterTicketByStatus(status);
    }

    public Set<Ticket> filterTicketByPriority(String priority){
        return tickets.filterTicketByPriority(priority);
    }

    public Set<Ticket> filterTicketByService(String service){
        return tickets.filterTicketByService(service);
    }

    public Set<Ticket> filterTicketByTechnician(String technician){
        return tickets.filterTicketByTechnician(technician);
    }

    public Set<Ticket> filterTicketByApplicant(String applicant){
        return tickets.filterTicketByApplicant(applicant);
    }

    public void saveDatas() {
        tickets.saveDatas();
    }
}


