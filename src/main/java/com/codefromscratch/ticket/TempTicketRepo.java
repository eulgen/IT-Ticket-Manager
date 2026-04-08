package com.codefromscratch.ticket;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TempTicketRepo implements TicketRepo {

    private final Set<Ticket> tickets;

    public TempTicketRepo() {
        this.tickets = new HashSet<Ticket>();
    }

    @Override
    public void saveDatas(@NonNull Set<Ticket> tickets) {
        tickets.stream().forEach(ticket -> this.tickets.add(ticket));
    }

    @Override
    public Set<Ticket> loadDatas() {
        return this.tickets;
    }

    @Override
    public void deleteDatas() {
        tickets.clear();
    }

    @Override
    public Ticket findTicketById(String id) {
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst().orElseThrow();
    }

    @Override
    public Ticket findTicketByTitle(String title) {
        return tickets.stream()
                .filter(ticket -> ticket.getTitle().equals(title))
                .findFirst().orElseThrow();
    }

    @Override
    public Set<Ticket> filterTicketByStatus(String status) {
        return tickets.stream()
                .filter(ticket -> ticket.getStatus().toString().contains(status))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> filterTicketByPriority(String priority) {
        return tickets.stream()
                .filter(ticket -> ticket.getPriority().toString().contains(priority))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> filterTicketByService(String service) {
        return tickets.stream()
                .filter(ticket -> ticket.getService().toString().contains(service))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> filterTicketByTechnician(String technician) {
        return tickets.stream()
                .filter(ticket -> ticket.getName_technician().contains(technician))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> filterTicketByApplicant(String applicant) {
        return tickets.stream()
                .filter(ticket -> ticket.getName_applicant().contains(applicant))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> filterTicketbyTitle(String title) {
        return Collections.singleton(tickets.stream()
                .filter(ticket -> ticket.getTitle().equals(title))
                .findFirst().orElseThrow());
    }

    @Override
    public void deleteTicket(String ticketId) {
        Ticket findedTicket = findTicketById(ticketId);
        tickets.remove(findedTicket);
    }

    @Override
    public void updateTicket(String id, Priority priority) {
        Ticket findedTicket = findTicketById(id);
        findedTicket.setPriority(priority);
        findedTicket.setUpdated_at(LocalDateTime.now());
        deleteTicket(id);
        tickets.add(findedTicket);
    }

    @Override
    public void updateTicket(String id, String technician) {
        Ticket findedTicket = findTicketById(id);
        findedTicket.setName_technician(technician);
        findedTicket.setUpdated_at(LocalDateTime.now());
        deleteTicket(id);
        tickets.add(findedTicket);
    }

    @Override
    public void updateTicket(String id, Status status) {
        Ticket findedTicket = findTicketById(id);
        findedTicket.changeStatus(status);
        findedTicket.setUpdated_at(LocalDateTime.now());
        deleteTicket(id);
        tickets.add(findedTicket);
    }

//    @Override
//    public void updateTicket(String id, String technician){
//        Ticket findedTicket = findTicketById(id);
//        findedTicket.setName_technician(technician);
//        findedTicket.setUpdated_at(LocalDateTime.now());
//        deleteTicket(id);
//    }
}
