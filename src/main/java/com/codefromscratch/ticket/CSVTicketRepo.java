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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CSVTicketRepo implements TicketRepo {

    private static final String TICKET_FILE = "tickets.csv";

    public CSVTicketRepo() {}

    public boolean fileExist(){
        return new File(TICKET_FILE).exists();
    }

    public void saveDatas(Set<Ticket> tickets) {
        if(!fileExist()){
            return;
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(TICKET_FILE))) {
            writer.writeNext(new String[]{"id",
                    "title",
                    "description",
                    "priority",
                    "status",
                    "service",
                    "name_applicant",
                    "name_technician",
                    "created_at",
                    "updated_at"});
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
            System.out.println("Datas loaded on :  " + TICKET_FILE);
            return tickets;
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void deleteDatas() {
        Set<Ticket> tickets = loadDatas();
        tickets.clear();
        saveDatas(tickets);
    }

    @Override
    public Ticket findTicketById(String id) {
        if(!fileExist()){
            return null;
        }
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(id)) {
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Priority priority = Priority.valueOf(nextLine[3]);
                    Status status = Status.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    return new Ticket(id, title, description, status, priority, service, name_applicant, name_technician, created_at,updated_at);
                }
            }

        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket findTicketByTitle(String title) {
        if(!fileExist()){ return null;}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while((nextLine = reader.readNext()) != null){
                if(nextLine[1].equals(title)){
                    String id = nextLine[0];
                    String description = nextLine[2];
                    Priority priority = Priority.valueOf(nextLine[3]);
                    Status status = Status.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    return new Ticket(id, title, description, status, priority, service, name_applicant, name_technician, created_at,updated_at);
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Ticket> filterTicketByStatus(String status) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[3].contains(status)) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, Status.valueOf(status), priority, service, name_applicant, name_technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Set<Ticket> filterTicketByPriority(String priority) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[4].equals(priority)) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, Priority.valueOf(priority), service, name_applicant, name_technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Set<Ticket> filterTicketByService(String service) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals(service)) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, Service.valueOf(service), name_applicant, name_technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Set<Ticket> filterTicketByTechnician(String technician) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[7].contains(technician)){
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, service, name_applicant, technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Set<Ticket> filterTicketByApplicant(String applicant) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[6].contains(applicant)) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, service, applicant, name_technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Set<Ticket> filterTicketbyTitle(String name) {
        Set<Ticket> tickets = new HashSet<>();
        if(!fileExist()){ return Set.of();}
        try(CSVReader reader = new CSVReader(new FileReader(TICKET_FILE))){
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[1].contains(name)){
                    String id = nextLine[0];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = LocalDateTime.parse(nextLine[8]);
                    LocalDateTime updated_at = LocalDateTime.parse(nextLine[9]);
                    tickets.add(new Ticket(id, name, description, status, priority, service, name_applicant, name_technician, created_at,updated_at));
                }
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void deleteTicket(String ticketId) {
        Set<Ticket> tickets = loadDatas();
        Ticket findedTicked =  tickets.stream()
                .filter(ticket -> ticket.getId().equals(ticketId))
                .findFirst().orElseThrow();
        tickets.remove(findedTicked);
        saveDatas(tickets);
    }

    @Override
    public void updateTicket(String id, Priority priority) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.setPriority(priority);
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }

    @Override
    public void updateTicket(String id, String technician) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.assignTechnician(technician);
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }

    @Override
    public void updateTicket(String id, Status status) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.setStatus(status);
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }
}
