package com.codefromscratch.ticket;

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

    public final Set<Ticket> mytickets;

    public CSVTicketRepo() {this.mytickets = new HashSet<>();}

    public boolean fileExist(){
        return new File(TICKET_FILE).exists();
    }

    public void saveData(Ticket ticket) {
        Set<Ticket> tickets = loadDatas();
        tickets.add(ticket);
        deleteDatas();
        saveDatas(tickets);
    }

    public void saveDatas(Set<Ticket> tickets) {
        File file = new File(TICKET_FILE);
        try{
            if(!fileExist()){
                file.createNewFile();
                return;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(TICKET_FILE))) {

            writer.writeNext(new String[]{"id",
                    "title",
                    "description",
                    "status",
                    "priority",
                    "service",
                    "name_applicant",
                    "name_technician",
                    "created_at",
                    "updated_at"});
            tickets.stream()
                    .map(ticket -> new String[]{ticket.getId(),
                            ticket.getTitle(),
                            ticket.getDescription(),
                            ticket.getStatus().toString(),
                            ticket.getPriority().toString(),
                            ticket.getService().toString(),
                            ticket.getName_applicant(),
                            ticket.getName_technician(),
                            ticket.getFormattedDateTime(ticket.getCreated_at()),
                            ticket.getFormattedDateTime(ticket.getUpdated_at())})
                    .forEach(writer::writeNext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDatas(){
        saveDatas(mytickets);
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
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 9) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);

                    Ticket ticket = new Ticket(id, title, description, status, priority, service, name_applicant, name_technician, created_at, updated_at);
                    tickets.add(ticket);
                }
            }
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
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
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
                if(nextLine[1].contains(title.toLowerCase())){
                    String id = nextLine[0];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
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
                    Status status_ticket = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status_ticket, priority, service, name_applicant, name_technician, created_at,updated_at));
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
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
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
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
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
                if(nextLine[7].contains(technician.toLowerCase())){
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String technician_ticket = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, service, name_applicant, technician_ticket, created_at,updated_at));
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
                if (nextLine[6].contains(applicant.toLowerCase())) {
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String applicant_ticket = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, service, applicant_ticket, name_technician, created_at,updated_at));
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
                if(nextLine[1].contains(name.toLowerCase())){
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String description = nextLine[2];
                    Status status = Status.valueOf(nextLine[3]);
                    Priority priority = Priority.valueOf(nextLine[4]);
                    Service service = Service.valueOf(nextLine[5]);
                    String name_applicant = nextLine[6];
                    String name_technician = nextLine[7];
                    LocalDateTime created_at = Ticket.parseStringToDate(nextLine[8]);
                    LocalDateTime updated_at = Ticket.parseStringToDate(nextLine[9]);
                    tickets.add(new Ticket(id, title, description, status, priority, service, name_applicant, name_technician, created_at,updated_at));
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
    public void updateTicketByPriority(String id, String priority) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.setPriority(Priority.valueOf(priority));
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }

    @Override
    public void updateTicketByTechnician(String id, String technician) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.assignTechnician(technician.toLowerCase());
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }

    @Override
    public void updateTicketByStatus(String id, String status) {
        Set<Ticket> tickets = loadDatas();
        tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .ifPresent(ticket -> {
                    ticket.changeStatus(Status.valueOf(status));
                    ticket.setUpdated_at(LocalDateTime.now());
                });
        saveDatas(tickets);
    }
}
