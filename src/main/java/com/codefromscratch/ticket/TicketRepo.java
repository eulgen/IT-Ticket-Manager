package com.codefromscratch.ticket;

import java.util.Optional;
import java.util.Set;

public interface TicketRepo {

    void saveData(Ticket ticket);
    void saveDatas(Set<Ticket> tickets);
    void saveDatas();
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
    void updateTicketByPriority(String id, String priority);
    void updateTicketByTechnician(String id, String technician);
    void updateTicketByStatus(String id);

}
