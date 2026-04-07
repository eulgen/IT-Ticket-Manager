package com.codefromscratch.ticket;

import java.util.Set;

public interface TicketRepo {

    void saveDatas(Set<Ticket> tickets);
    Set<Ticket> loadDatas();
    void deleteDatas();
    Ticket findTicketById(String id);
    Ticket findTicketByTitle(String title);
    Set<Ticket> filterTicketByStatus(String status);
    Set<Ticket> filterTicketByPriority(String priority);
    Set<Ticket> filterTicketByService(String service);
    Set<Ticket> filterTicketByTechnician(String technician);
    Set<Ticket> filterTicketByApplicant(String applicant);
    Set<Ticket> filterTicketbyTitle(String name);
    void deleteTicket(String ticketId);
    void updateTicket(String id, Priority priority);
    void updateTicket(String id, String technician);
    void updateTicket(String id, Status status);

}
