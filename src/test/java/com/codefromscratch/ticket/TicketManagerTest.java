package com.codefromscratch.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketManagerTest {

    @Mock
    private TicketRepo ticketRepo;

    @InjectMocks
    private TicketManager ticketManager;

    @BeforeEach
    void setUp() {
        ticketManager = new TicketManager(ticketRepo);
    }

    @Test
    void saveData() {
        Ticket ticket = new Ticket("Pas de connexion Internet", "La connexion Internet ne passe pas depuis hier soir", Status.OPEN, Priority.HIGH, Service.MANAGEMENT, "Takam Jules", "Onana Alex");
        ticketManager.saveData(ticket);
        verify(ticketRepo).saveData(ticket);
        verifyNoMoreInteractions(ticketRepo);
    }


    @Test
    void createTicket() {
        ticketManager.createTicket("PC hors jeu", "Le PC ne fonctionne plus", Priority.HIGH, Service.MANAGEMENT, "Lolita", "John Doe");
        ArgumentCaptor<Ticket> captor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepo).saveData(captor.capture());

        Ticket savedTicket = captor.getValue();
        assertEquals("pc hors jeu", savedTicket.getTitle());
        assertEquals("le pc ne fonctionne plus", savedTicket.getDescription());
        assertEquals(Status.OPEN, savedTicket.getStatus());
        assertEquals(Priority.HIGH, savedTicket.getPriority());
        assertEquals(Service.MANAGEMENT, savedTicket.getService());
        assertEquals("lolita", savedTicket.getName_applicant());
        assertEquals("john doe", savedTicket.getName_technician());
        assertNotNull(savedTicket.getId());
        assertNotNull(savedTicket.getCreated_at());
        assertNotNull(savedTicket.getUpdated_at());
    }

    @Test
    void saveDatas() {
        ticketManager.saveDatas();
        verify(ticketRepo).saveDatas();
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void loadDatas() {
        Set<Ticket> tickets = Set.of(new Ticket("Erreur imprimante", "Impression bloquee", Status.OPEN, Priority.MEDIUM, Service.SUPPORT, "Alice", "Bob"));
        when(ticketRepo.loadDatas()).thenReturn(tickets);

        Set<Ticket> result = ticketManager.loadDatas();

        assertSame(tickets, result);
        verify(ticketRepo).loadDatas();
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void deleteAll() {
        ticketManager.deleteAll();

        verify(ticketRepo).deleteDatas();
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void findTicketById() {
        Ticket ticket = new Ticket("Tick-EG-123456", "Erreur reseau", "Pas de connexion", Status.OPEN, Priority.HIGH, Service.SUPPORT, "Alice", "Bob", LocalDateTime.of(2026, 4, 14, 10, 0), LocalDateTime.of(2026, 4, 14, 11, 0));
        when(ticketRepo.findTicketById("Tick-EG-123456")).thenReturn(ticket);

        Ticket result = ticketManager.findTicketById("Tick-EG-123456");

        assertSame(ticket, result);
        verify(ticketRepo).findTicketById("Tick-EG-123456");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void findTicketByTitle() {
        Ticket ticket = new Ticket("Erreur reseau", "Pas de connexion", Status.OPEN, Priority.HIGH, Service.SUPPORT, "Alice", "Bob");
        when(ticketRepo.findTicketByTitle("Erreur reseau")).thenReturn(ticket);

        Ticket result = ticketManager.findTicketByTitle("Erreur reseau");

        assertSame(ticket, result);
        verify(ticketRepo).findTicketByTitle("Erreur reseau");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void updateTicketByPriority() {
        ticketManager.updateTicketByPriority("Tick-EG-654321", "CRITICAL");

        verify(ticketRepo).updateTicketByPriority("Tick-EG-654321", "CRITICAL");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void updateTicketByTechnician() {
        ticketManager.updateTicketByTechnician("Tick-EG-654321", "john doe");

        verify(ticketRepo).updateTicketByTechnician("Tick-EG-654321", "john doe");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void updateTicketByStatus() {
        ticketManager.updateTicketByStatus("Tick-EG-654321");

        verify(ticketRepo).updateTicketByStatus("Tick-EG-654321");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void deleteTicket() {
        ticketManager.deleteTicket("Tick-EG-654321");

        verify(ticketRepo).deleteTicket("Tick-EG-654321");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void listAllTickets() {
        Ticket ticket1 = new Ticket("Tick-EG-111111", "reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob", LocalDateTime.of(2026, 4, 14, 8, 0), LocalDateTime.of(2026, 4, 14, 9, 0));
        Ticket ticket2 = new Ticket("Tick-EG-222222", "imprimante", "incident imprimante", Status.RESOLVED, Priority.MEDIUM, Service.MANAGEMENT, "charles", "diane", LocalDateTime.of(2026, 4, 13, 14, 30), LocalDateTime.of(2026, 4, 13, 16, 45));
        Set<Ticket> tickets = new LinkedHashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);
        when(ticketRepo.loadDatas()).thenReturn(tickets);

        String result = ticketManager.listAllTickets();

        String expected = "Tick-EG-111111 - reseau - incident reseau - OPEN - HIGH - SUPPORT - alice - bob - 14/04/2026 à 08:00 - 14/04/2026 à 09:00\n"
                + "Tick-EG-222222 - imprimante - incident imprimante - RESOLVED - MEDIUM - MANAGEMENT - charles - diane - 13/04/2026 à 14:30 - 13/04/2026 à 16:45";
        assertEquals(expected, result);
        verify(ticketRepo).loadDatas();
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketbyTitle() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketbyTitle("reseau")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketbyTitle("reseau");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketbyTitle("reseau");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketByStatus() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketByStatus("OPEN")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketByStatus("OPEN");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketByStatus("OPEN");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketByPriority() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketByPriority("HIGH")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketByPriority("HIGH");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketByPriority("HIGH");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketByService() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketByService("SUPPORT")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketByService("SUPPORT");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketByService("SUPPORT");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketByTechnician() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketByTechnician("bob")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketByTechnician("bob");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketByTechnician("bob");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void filterTicketByApplicant() {
        Set<Ticket> tickets = Set.of(new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"));
        when(ticketRepo.filterTicketByApplicant("alice")).thenReturn(tickets);

        Set<Ticket> result = ticketManager.filterTicketByApplicant("alice");

        assertSame(tickets, result);
        verify(ticketRepo).filterTicketByApplicant("alice");
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void testSaveDatas() {
        Set<Ticket> tickets = Set.of(
                new Ticket("reseau", "incident reseau", Status.OPEN, Priority.HIGH, Service.SUPPORT, "alice", "bob"),
                new Ticket("imprimante", "incident imprimante", Status.RESOLVED, Priority.MEDIUM, Service.MANAGEMENT, "charles", "diane")
        );

        ticketManager.saveDatas(tickets);

        verify(ticketRepo).saveDatas(tickets);
        verifyNoMoreInteractions(ticketRepo);
    }

    @Test
    void getTickets() {
        assertSame(ticketRepo, ticketManager.getTickets());
    }
}
