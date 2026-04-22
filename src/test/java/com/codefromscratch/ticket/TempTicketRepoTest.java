package com.codefromscratch.ticket;

import com.codefromscratch.inmemory.TempTicketRepo;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TempTicketRepoTest {

    @Test
    void saveData() {
        TempTicketRepo tempTicketRepo = new TempTicketRepo();
        Ticket ticket = new Ticket("Pas de connexion Internet", "La connexion Internet ne passe pas depuis hier soir",
                Status.OPEN,Priority.HIGH,Service.MANAGEMENT,"Takam Jules","Onana Alex");
        tempTicketRepo.saveData(ticket);
        Set<Ticket> tickets = tempTicketRepo.loadDatas();
        assertEquals(1,tickets.size());
    }

    @Test
    void saveDatas() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        Set<Ticket> tickets = repo.loadDatas();
        repo.saveDatas(tickets);
        assertEquals(repo.getTickets().size(), repo.loadDatas().size());
    }

    @Test
    void testSaveDatas() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveDatas();
        assertEquals(repo.getTickets().size(), repo.loadDatas().size());
    }

    @Test
    void loadDatas() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        assertEquals(repo.getTickets().size(), repo.loadDatas().size());
    }

    @Test
    void deleteDatas() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.deleteDatas();
        assertEquals(0, repo.loadDatas().size());
    }

    @Test
    void findTicketById() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        Ticket findedTicket = repo.findTicketById(t1.getId());
        assertEquals(t1.getId(), findedTicket.getId());
    }

    @Test
    void findTicketByTitle() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        Ticket findedTicket = repo.findTicketByTitle(t1.getTitle());
        assertEquals(t1.getTitle(), findedTicket.getTitle());
    }

    @Test
    void filterTicketByStatus() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        Set<Ticket> foundTicket = repo.filterTicketByStatus(t1.getStatus().toString());
        assertEquals(2, foundTicket.size());
    }

    @Test
    void filterTicketByPriority() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 3", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        Set<Ticket> foundTicket = repo.filterTicketByPriority(t1.getPriority().toString());
        assertEquals(2, foundTicket.size());
    }

    @Test
    void filterTicketByService() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.MANAGEMENT, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        Set<Ticket> foundTicket = repo.filterTicketByService(t1.getService().toString());
        assertEquals(3, foundTicket.size());
    }

    @Test
    void filterTicketByTechnician() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        Set<Ticket> foundTicket = repo.filterTicketByTechnician(t1.getName_technician());
        assertEquals(2, foundTicket.size());
    }

    @Test
    void filterTicketByApplicant() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        Set<Ticket> foundTicket = repo.filterTicketByStatus(t1.getStatus().toString());
        assertEquals(2, foundTicket.size());
    }

    @Test
    void filterTicketbyTitle() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(2, repo.filterTicketbyTitle(t1.getTitle().toString()).size());
    }

    @Test
    void deleteTicket() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 1", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        repo.deleteTicket(t1.getId());
        assertNotEquals(3, repo.loadDatas().size());
    }

    @Test
    void updateTicketByPriority() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByPriority(t2.getId(), Priority.CRITICAL.toString());
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertEquals(Priority.CRITICAL, ticketChanged.getPriority());
    }

    @Test
    void updateTicketByTechnician() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByTechnician(t2.getId(), "Alex Kuate");
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertEquals("Alex Kuate", ticketChanged.getName_technician());
    }

    @Test
    void updateTicketByStatus() {
        TempTicketRepo repo = new TempTicketRepo();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByStatus(t2.getId());
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertNotEquals(Status.IN_PROGRESS.toString(), ticketChanged.getStatus());
    }
}