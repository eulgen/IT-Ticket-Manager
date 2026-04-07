package com.codefromscratch.ticket;

import com.codefromscratch.employee.Service;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CSVTicketRepo implements TicketRepo {

    private static final String TICKET_FILE = "tickets.csv";

    public void saveDatas(Set<Ticket> tickets) {

        boolean fileExists = new File(TICKET_FILE).exists();

        try (CSVWriter writer = new CSVWriter(new FileWriter(TICKET_FILE, true))) {
            if (!fileExists) {
                writer.writeNext(new String[]{"id", "title", "description", "priority", "status", "service", "name_applicant", "name_technician", "created_at", "updated_at"});
            }
            tickets.stream()
                    .map(ticket -> new String[]{ticket.getId(),
                            ticket.getTitle(),
                            ticket.getDescription(),
                            ticket.getPriority().toString(),
                            ticket.getStatus().toString(),
                            ticket.getService().toString(),
                            ticket.getName_applicant(),
                            ticket.getName_technician(),
                            ticket.getCreated_at().toString(),
                            ticket.getUpdated_at().toString()})
                    .forEach(writer::writeNext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Ticket> loadDatas() {
        Set<Ticket> tickets = new HashSet<>();
        File file = new File(TICKET_FILE);

        if (!file.exists()) {
            System.out.println("Le fichier " + TICKET_FILE + " n'existe pas.");
            return tickets;
        }

        try (CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))) {
            String[] nextLine;
            // Lire l'en-tête pour le sauter
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 9) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Priority priority = Priority.valueOf(nextLine[3]);
                    Status status = Status.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);

                    Ticket ticket = new Ticket(id, title, description, status, priority, service, name_applicant, name_technician, created_at, updated_at);
                    tickets.add(ticket);
                }
            }
            System.out.println("Données chargées depuis " + TICKET_FILE);
            return tickets;
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format pour l'âge dans le fichier CSV.");
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void deleteDatas() {

    }

    @Override
    public Ticket findTicketById(String id) {
        return null;
    }

    @Override
    public Ticket findTicketByTitle(String title) {
        return null;
    }

    @Override
    public Set<Ticket> filterTicketByStatus(String status) {
        return Set.of();
    }

    @Override
    public Set<Ticket> filterTicketByPriority(String priority) {
        return Set.of();
    }

    @Override
    public Set<Ticket> filterTicketByService(String service) {
        return Set.of();
    }

    @Override
    public Set<Ticket> filterTicketByTechnician(String technician) {
        return Set.of();
    }

    @Override
    public Set<Ticket> filterTicketByApplicant(String applicant) {
        return Set.of();
    }

    @Override
    public Set<Ticket> filterTicketbyTitle(String name) {
        return Set.of();
    }

    @Override
    public void deleteTicket(String ticketId) {

    }

    @Override
    public void updateTicket(String id, Priority priority) {

    }

    @Override
    public void updateTicket(String id, String technician) {

    }

    @Override
    public void updateTicket(String id, Status status) {

    }
}
