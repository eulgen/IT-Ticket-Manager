package com.codefromscratch.simulation;

import com.codefromscratch.ticket.CSVTicketRepo;
import com.codefromscratch.ticket.Priority;
import com.codefromscratch.ticket.Service;
import com.codefromscratch.ticket.Status;
import com.codefromscratch.ticket.TempTicketRepo;
import com.codefromscratch.ticket.Ticket;
import com.codefromscratch.ticket.TicketManager;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;

public final class Simulation {

    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Scanner scanner;
    private TicketManager ticketManager;

    public Simulation() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        clearScreen();
        printBanner();
        bootAnimation();
        chooseRepository();
        menuLoop();
    }

    private void bootAnimation() {
        animate("Loading console workspace", 3, 120);
        animate("Loading ticket manager services", 3, 120);
    }

    private void chooseRepository() {
        section("Repository selection");
        System.out.println("1. In-memory repository");
        System.out.println("2. CSV repository");

        while (ticketManager == null) {
            String choice = prompt("Choose repository [1-2]: ");
            switch (choice) {
                case "1" -> {
                    ticketManager = new TicketManager(new TempTicketRepo());
                    success("In-memory repository selected.");
                }
                case "2" -> {
                    ticketManager = new TicketManager(new CSVTicketRepo());
                    success("CSV repository selected.");
                }
                default -> error("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    private void menuLoop() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt("Select an action: ");
            switch (choice) {
                case "1" -> createTicket();
                case "2" -> listAllTickets();
                case "3" -> findTicketById();
                case "4" -> findTicketByTitle();
                case "5" -> updateTicketPriority();
                case "6" -> updateTicketTechnician();
                case "7" -> updateTicketStatus();
                case "8" -> deleteTicket();
                case "9" -> filterTickets();
                case "10" -> reloadTickets();
                case "11" -> saveTickets();
                case "12" -> deleteAllTickets();
                case "0" -> running = exit();
                default -> error("Unknown action. Try again.");
            }
        }
    }

    private void printMenu() {
        section("Main menu");
        System.out.println(BLUE + "1." + RESET + " Create a ticket");
        System.out.println(BLUE + "2." + RESET + " List all tickets");
        System.out.println(BLUE + "3." + RESET + " Find ticket by ID");
        System.out.println(BLUE + "4." + RESET + " Find ticket by title");
        System.out.println(BLUE + "5." + RESET + " Update ticket priority");
        System.out.println(BLUE + "6." + RESET + " Update ticket technician");
        System.out.println(BLUE + "7." + RESET + " Advance ticket status");
        System.out.println(BLUE + "8." + RESET + " Delete one ticket");
        System.out.println(BLUE + "9." + RESET + " Filter tickets");
        System.out.println(BLUE + "10." + RESET + " Reload tickets");
        System.out.println(BLUE + "11." + RESET + " Save tickets");
        System.out.println(BLUE + "12." + RESET + " Delete all tickets");
        System.out.println(BLUE + "0." + RESET + " Exit");
    }

    private void createTicket() {
        section("Create ticket");
        try {
            String title = promptRequired("Title: ");
            String description = promptRequired("Description: ");
            Priority priority = choosePriority();
            Service service = chooseService();
            String applicant = promptRequired("Applicant name: ");
            String technician = promptOptional("Technician name (leave empty for not assign): ");

            ticketManager.createTicket(
                    title,
                    description,
                    priority,
                    service,
                    applicant,
                    technician.isBlank() ? "NOT ASSIGN" : technician
            );

            animate("Creating ticket", 2, 100);
            success("Ticket created successfully.");
            listAllTickets();
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private void listAllTickets() {
        section("All tickets");
        printTickets(ticketManager.loadDatas());
    }

    private void findTicketById() {
        section("Find by ID");
        String id = promptRequired("Ticket ID: ");
        try {
            Ticket ticket = ticketManager.findTicketById(id);
            success("Ticket found.");
            printTicketCard(ticket);
        } catch (RuntimeException e) {
            error("No ticket found with ID: " + id);
        }
    }

    private void findTicketByTitle() {
        section("Find by title");
        String title = promptRequired("Title or title fragment: ");
        try {
            Ticket ticket = ticketManager.findTicketByTitle(title.toLowerCase());
            success("Ticket found.");
            printTicketCard(ticket);
        } catch (RuntimeException e) {
            error("No ticket found for title: " + title);
        }
    }

    private void updateTicketPriority() {
        section("Update priority");
        String id = promptRequired("Ticket ID: ");
        Priority priority = choosePriority();
        try {
            ticketManager.updateTicketByPriority(id, priority.name());
            animate("Updating priority", 2, 100);
            success("Priority updated.");
            printTicketCard(ticketManager.findTicketById(id));
        } catch (RuntimeException e) {
            error("Unable to update priority for ticket: " + id);
        }
    }

    private void updateTicketTechnician() {
        section("Update technician");
        String id = promptRequired("Ticket ID: ");
        String technician = promptRequired("New technician: ");
        try {
            ticketManager.updateTicketByTechnician(id, technician.toLowerCase());
            animate("Updating technician", 2, 100);
            success("Technician updated.");
            printTicketCard(ticketManager.findTicketById(id));
        } catch (RuntimeException e) {
            error("Unable to update technician for ticket: " + id);
        }
    }

    private void updateTicketStatus() {
        section("Advance status");
        String id = promptRequired("Ticket ID: ");
        try {
            Ticket before = ticketManager.findTicketById(id);
            Status previousStatus = before.getStatus();
            ticketManager.updateTicketByStatus(id);
            Ticket after = ticketManager.findTicketById(id);
            animate("Advancing status", 2, 100);
            success("Status changed from " + previousStatus + " to " + after.getStatus() + ".");
            printTicketCard(after);
        } catch (RuntimeException e) {
            error("Unable to advance status for ticket: " + id);
        }
    }

    private void deleteTicket() {
        section("Delete ticket");
        String id = promptRequired("Ticket ID: ");
        try {
            ticketManager.deleteTicket(id);
            animate("Deleting ticket", 2, 100);
            success("Ticket deleted.");
        } catch (RuntimeException e) {
            error("Unable to delete ticket: " + id);
        }
    }

    private void filterTickets() {
        section("Filter tickets");
        System.out.println("1. By title");
        System.out.println("2. By status");
        System.out.println("3. By priority");
        System.out.println("4. By service");
        System.out.println("5. By technician");
        System.out.println("6. By applicant");

        String choice = prompt("Choose filter [1-6]: ");
        Set<Ticket> filteredTickets;

        try {
            switch (choice) {
                case "1" -> filteredTickets = ticketManager.filterTicketbyTitle(promptRequired("Title fragment: ").toLowerCase());
                case "2" -> filteredTickets = ticketManager.filterTicketByStatus(chooseStatus().name());
                case "3" -> filteredTickets = ticketManager.filterTicketByPriority(choosePriority().name());
                case "4" -> filteredTickets = ticketManager.filterTicketByService(chooseService().name());
                case "5" -> filteredTickets = ticketManager.filterTicketByTechnician(promptRequired("Technician: ").toLowerCase());
                case "6" -> filteredTickets = ticketManager.filterTicketByApplicant(promptRequired("Applicant: ").toLowerCase());
                default -> {
                    error("Invalid filter choice.");
                    return;
                }
            }
            printTickets(filteredTickets);
        } catch (RuntimeException e) {
            error("Unable to filter tickets.");
        }
    }

    private void reloadTickets() {
        section("Reload tickets");
        animate("Reloading data from repository", 2, 100);
        printTickets(ticketManager.loadDatas());
    }

    private void saveTickets() {
        section("Save tickets");
        try {
            ticketManager.saveDatas();
            animate("Saving tickets", 2, 100);
            success("Save operation completed.");
        } catch (RuntimeException e) {
            error("Save operation failed.");
        }
    }

    private void deleteAllTickets() {
        section("Delete all tickets");
        String confirm = prompt("Type DELETE to confirm: ");
        if (!"DELETE".equals(confirm)) {
            error("Operation cancelled.");
            return;
        }
        ticketManager.deleteAll();
        animate("Deleting all tickets", 2, 100);
        success("All tickets deleted.");
    }

    private boolean exit() {
        section("Exit");
        animate("Closing simulation", 2, 100);
        System.out.println(GREEN + "Session finished." + RESET);
        return false;
    }

    private Priority choosePriority() {
        System.out.println("Available priorities:");
        Priority[] priorities = Priority.values();
        for (int i = 0; i < priorities.length; i++) {
            System.out.println((i + 1) + ". " + priorities[i]);
        }
        while (true) {
            String choice = prompt("Choose priority: ");
            try {
                int index = Integer.parseInt(choice);
                if (index >= 1 && index <= priorities.length) {
                    return priorities[index - 1];
                }
            } catch (NumberFormatException ignored) {
            }

            try {
                return Priority.valueOf(choice.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
            error("Invalid priority.");
        }
    }

    private Service chooseService() {
        System.out.println("Available services:");
        Service[] services = Service.values();
        for (int i = 0; i < services.length; i++) {
            System.out.println((i + 1) + ". " + services[i]);
        }
        while (true) {
            String choice = prompt("Choose service: ");
            try {
                int index = Integer.parseInt(choice);
                if (index >= 1 && index <= services.length) {
                    return services[index - 1];
                }
            } catch (NumberFormatException ignored) {
            }

            try {
                return Service.valueOf(choice.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
            error("Invalid service.");
        }
    }

    private Status chooseStatus() {
        System.out.println("Available status values:");
        Status[] statuses = Status.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        while (true) {
            String choice = prompt("Choose status: ");
            try {
                int index = Integer.parseInt(choice);
                if (index >= 1 && index <= statuses.length) {
                    return statuses[index - 1];
                }
            } catch (NumberFormatException ignored) {
            }

            try {
                return Status.valueOf(choice.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
            error("Invalid status.");
        }
    }

    private String prompt(String label) {
        System.out.print(CYAN + label + RESET);
        return scanner.nextLine().trim();
    }

    private String promptRequired(String label) {
        while (true) {
            String value = prompt(label);
            if (!value.isBlank()) {
                return value;
            }
            error("This field is required.");
        }
    }

    private String promptOptional(String label) {
        return prompt(label);
    }

    private void printBanner() {
        System.out.println(CYAN);
        System.out.println("==============================================================");
        System.out.println("                    TICKET MANAGER CONSOLE                    ");
        System.out.println("==============================================================");
        System.out.println("     Create, search, update, filter, save and delete tickets ");
        System.out.println("==============================================================" + RESET);
    }

    private void section(String title) {
        System.out.println();
        System.out.println(YELLOW + "--------------------------------------------------------------");
        System.out.println(title.toUpperCase());
        System.out.println("--------------------------------------------------------------" + RESET);
    }

    private void animate(String message, int steps, long delayMs) {
        System.out.print(message);
        for (int i = 0; i < steps; i++) {
            pause(delayMs);
            System.out.print(".");
        }
        System.out.println();
    }

    private void success(String message) {
        System.out.println(GREEN + message + RESET);
    }

    private void error(String message) {
        System.out.println(RED + message + RESET);
    }

    private void printTickets(Set<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            error("No tickets available.");
            return;
        }

        tickets.stream()
                .sorted(Comparator.comparing(Ticket::getCreated_at))
                .forEach(this::printTicketCard);
    }

    private void printTicketCard(Ticket ticket) {
        System.out.println(GREEN + "+------------------------------------------------------------+");
        System.out.printf("| %-58s |%n", "ID: " + ticket.getId());
        System.out.printf("| %-58s |%n", "Title: " + ticket.getTitle());
        System.out.printf("| %-58s |%n", "Description: " + ticket.getDescription());
        System.out.printf("| %-58s |%n", "Status: " + ticket.getStatus() + " | Priority: " + ticket.getPriority());
        System.out.printf("| %-58s |%n", "Service: " + ticket.getService());
        System.out.printf("| %-58s |%n", "Applicant: " + ticket.getName_applicant());
        System.out.printf("| %-58s |%n", "Technician: " + ticket.getName_technician());
        System.out.printf("| %-58s |%n", "Created: " + DATE_FORMAT.format(ticket.getCreated_at()));
        System.out.printf("| %-58s |%n", "Updated: " + DATE_FORMAT.format(ticket.getUpdated_at()));
        System.out.println("+------------------------------------------------------------+" + RESET);
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
