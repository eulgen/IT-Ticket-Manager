# Ticket Manager

A simple Java application for managing internal support tickets. The project is built around a focused ticketing domain model, a service layer (`TicketManager`), two repository implementations, and an interactive console simulation that lets you execute ticket operations manually.

## Features

- **Ticket creation**: Create tickets with a title, description, priority, service, applicant name, and technician name.
- **Ticket lifecycle**: Track ticket status through `OPEN`, `IN_PROGRESS`, `RESOLVED`, and `CLOSED`.
- **Ticket updates**: Update ticket priority, assigned technician, and status.
- **Filtering and search**: Find or filter tickets by ID, title, status, priority, service, technician, or applicant.
- **Time-based filtering**: Filter tickets created within a period defined by a number of days.
- **Two persistence strategies**:
  - `TempTicketRepo`: stores tickets in memory with a `Set<Ticket>`.
  - `CSVTicketRepo`: persists tickets to `tickets.csv`.
- **Console simulation**:
  - `Simulation` provides an interactive console menu
  - you can manually create, search, update, filter, save, list, and delete tickets
  - you can also filter tickets by creation period using a number of days
  - the console output includes formatted ticket cards and lightweight terminal animations
- **Automated tests**:
  - repository tests for the in-memory and CSV implementations
  - Mockito-based unit tests for `TicketManager`

## Project Structure

The project follows a standard Maven structure:

- `src/main/java/com/codefromscratch/`: Main source code.
  - `ticket/`: Core ticketing logic.
    - `Ticket`: domain entity with ID generation, timestamps, status changes, and formatting helpers
    - `TicketManager`: application service that delegates ticket operations to a repository
    - `TicketRepo`: repository contract
    - `TempTicketRepo`: in-memory repository implementation
    - `CSVTicketRepo`: CSV file repository implementation using OpenCSV
    - `Priority`, `Status`, `Service`: enums used by the ticket model
  - `simulation/`: Console demonstration layer.
    - `Simulation`: interactive console controller built on top of `TicketManager`
  - `Main.java`: application entry point that launches the simulation
- `src/test/java/com/codefromscratch/ticket/`: Unit tests for repositories and `TicketManager`

## Prerequisites

- Java 23 or higher
- Maven
- Lombok support enabled in your IDE

## How to Run

1. Clone the repository.
2. Build the project with Maven:
   ```bash
   mvn clean install
   ```
3. Run the application entry point:
   ```bash
   mvn exec:java -Dexec.mainClass="com.codefromscratch.Main"
   ```
4. Run the test suite:
   ```bash
   mvn test
   ```

## Console Demo

Running `Main` starts the interactive console simulation in `com.codefromscratch.simulation.Simulation`. From the terminal, you can:

- choose the repository implementation to use
- create tickets manually
- list all tickets
- search by ID or title
- update priority, technician, and status
- filter tickets by title, status, priority, service, technician, or applicant
- filter tickets by creation period in days
- save tickets and reload repository data
- delete one ticket or clear all tickets

## Example Usage

The core workflow is centered around `TicketManager` and a `TicketRepo` implementation:

```java
TicketRepo ticketRepo = new TempTicketRepo();
TicketManager ticketManager = new TicketManager(ticketRepo);

ticketManager.createTicket(
        "PC hors jeu",
        "Le PC ne fonctionne plus",
        Priority.HIGH,
        Service.MANAGEMENT,
        "Lolita",
        "John Doe"
);

ticketManager.createTicket(
        "Probleme reseau",
        "Le reseau ne fonctionne pas depuis hier",
        Priority.MEDIUM,
        Service.SUPPORT,
        "Alicia",
        "Alex"
);

System.out.println(ticketManager.listAllTickets());
```

To persist tickets in a CSV file instead of memory, replace `TempTicketRepo` with `CSVTicketRepo`.

## Implementation Notes

- `TicketManager#createTicket(...)` normalizes text values to lowercase before saving.
- `TicketManager#filterTicketByPeriodTime(long)` returns tickets created within the last `N` days.
- `Ticket#changeStatus()` advances the workflow one step at a time:
  - `OPEN -> IN_PROGRESS`
  - `IN_PROGRESS -> RESOLVED`
  - `RESOLVED -> CLOSED`
- `CSVTicketRepo` stores data in the project-level `tickets.csv` file.
- `TempTicketRepo` is the safest option for demos and tests because it avoids file writes.

## Technologies Used

- **Java 23**
- **Maven**
- **OpenCSV**
- **Lombok**
- **JUnit 5**
- **Mockito**
