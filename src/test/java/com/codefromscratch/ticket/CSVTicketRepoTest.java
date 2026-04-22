package com.codefromscratch.ticket;

import com.codefromscratch.inmemory.CSVTicketRepo;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CSVTicketRepoTest {


    @Test
    void fileExist() {
        CSVTicketRepo csvTicketRepo = new CSVTicketRepo();
        assertTrue(csvTicketRepo.fileExist());
    }

    @Test
    void saveData() {
        CSVTicketRepo csvTicketRepo = new CSVTicketRepo();
        csvTicketRepo.deleteDatas();
        Ticket ticket = new Ticket("Pas de connexion Internet", "La connexion Internet ne passe pas depuis hier soir",
                Status.OPEN,Priority.HIGH,Service.MANAGEMENT,"Takam Jules","Onana Alex");
        csvTicketRepo.saveData(ticket);
        Set<Ticket> tickets = csvTicketRepo.loadDatas();
        assertEquals(1,tickets.size());
    }

    @Test
    void saveDatas() {
        CSVTicketRepo csvTicketRepo = new CSVTicketRepo();
        csvTicketRepo.deleteDatas();
        Set<Ticket> tickets = csvTicketRepo.loadDatas();
        assertEquals(tickets.size(), 0);
        Ticket ticket1 = new Ticket("Pas de connexion Internet", "La connexion Internet ne passe pas depuis hier soir",
                Status.OPEN,Priority.HIGH,Service.MANAGEMENT,"Takam Jules","Onana Alex");
        Ticket ticket2 = new Ticket("Imprimante ne fonctionne pas","Depuis lundi dernier l'imprimante ne fonctionne pas",Status.OPEN, Priority.MEDIUM, Service.LOGISTICS,"Pamela","John Doe");
        tickets.add(ticket1);
        tickets.add(ticket2);
        csvTicketRepo.saveDatas(tickets);
        assertEquals(tickets.size(), csvTicketRepo.loadDatas().size());
    }

    @Test
    void testSaveDatas() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.addToMyTickets(t1);
        repo.addToMyTickets(t2);
        repo.saveDatas();
        assertEquals(repo.getMytickets().size(), repo.loadDatas().size());
    }

    @Test
    void loadDatas() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Set<Ticket> tickets1 = repo.loadDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        Set<Ticket> tickets2 = repo.loadDatas();
        assertEquals(tickets1.size(),0);
        assertEquals(tickets2.size(), 1);
    }

    @Test
    void deleteDatas() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        assertEquals(0, repo.loadDatas().size());
    }

    @Test
    void findTicketById() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        assertEquals(t2.getId(), repo.findTicketById(t2.getId()).getId());
    }

    @Test
    void findTicketByTitle() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(t1.getTitle(), repo.findTicketByTitle(t3.getTitle()).getTitle());
    }

    @Test
    void filterTicketByStatus() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(2, repo.filterTicketByStatus(t1.getStatus().toString()).size());
    }

    @Test
    void filterTicketByPriority() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 3", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(2, repo.filterTicketByPriority(t1.getPriority().toString()).size());
    }

    @Test
    void filterTicketByService() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 3", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(2, repo.filterTicketByService(t1.getService().toString()).size());
    }

    @Test
    void filterTicketByTechnician() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 3", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(1,repo.filterTicketByTechnician(t1.getName_technician()).size());
    }

    @Test
    void filterTicketByApplicant() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 3", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(1,repo.filterTicketByApplicant(t2.getName_applicant()).size());
    }

    @Test
    void filterTicketbyTitle() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 1", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        assertEquals(2, repo.filterTicketbyTitle(t1.getTitle()).size());
    }

    @Test
    void deleteTicket() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        Ticket t3 = new Ticket("Titre 3", "Desc 3", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alicia", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.saveData(t3);
        repo.deleteTicket(t1.getId());
        assertNotEquals(t1, repo.findTicketById(t1.getId()));
    }

    @Test
    void updateTicketByPriority() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByPriority(t2.getId(), Priority.CRITICAL.toString());
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertEquals(Priority.CRITICAL, ticketChanged.getPriority());
        assertNotEquals(ticketChanged.getUpdated_at(), t2.getUpdated_at());
    }

    @Test
    void updateTicketByTechnician() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByTechnician(t2.getId(), "Alex Kuate");
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertEquals("Alex Kuate".toLowerCase(), ticketChanged.getName_technician());
        assertNotEquals(ticketChanged.getUpdated_at(), t2.getUpdated_at());
    }

    @Test
    void updateTicketByStatus() {
        CSVTicketRepo repo = new CSVTicketRepo();
        repo.deleteDatas();
        Ticket t1 = new Ticket("Titre 1", "Desc 1", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Alice", "Bob");
        Ticket t2 = new Ticket("Titre 2", "Desc 2", Status.IN_PROGRESS, Priority.MEDIUM, Service.LOGISTICS, "Charlie", "Dave");
        repo.saveData(t1);
        repo.saveData(t2);
        repo.updateTicketByStatus(t2.getId());
        Ticket ticketChanged = repo.findTicketById(t2.getId());
        assertNotEquals(Status.IN_PROGRESS.toString(), ticketChanged.getStatus());
        assertNotEquals(ticketChanged.getUpdated_at(), t2.getUpdated_at());
    }
}