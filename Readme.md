# Ticket Manager

A simple Java application to manage support tickets within an organization. It allows creating, updating, and filtering tickets based on various criteria like priority, status, and service.

## Features

- **Ticket Creation**: Create tickets with title, description, priority, and target service.
- **Role-based Management**: Different roles for users:
    - **Administrator**: Can create, update, delete, and filter all tickets.
    - **Technician**: Handles support requests.
    - **Applicant**: Submits support requests.
- **Filtering**: Filter tickets by title, status, priority, service, technician, or applicant.
- **Status Tracking**: Monitor ticket progress through stages (OPEN, IN_PROGRESS, RESOLVED, CLOSED).

## Project Structure

The project follows a standard Maven structure:

- `src/main/java/com/codefromscratch/`: Main source code.
    - `employee/`: Contains employee-related classes (`Administrator`, `Technician`, `Applicant`, `Service`, etc.).
    - `ticket/`: Contains ticket-related classes (`Ticket`, `Priority`, `Status`).
    - `Main.java`: Entry point of the application.

## Prerequisites

- Java 23 or higher.
- Maven.
- Lombok (make sure your IDE is configured to support it).

## How to Run

1. Clone the repository.
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the `Main` class:
   ```bash
   mvn exec:java -Dexec.mainClass="com.codefromscratch.Main"
   ```

## Example Usage

The `Main.java` file demonstrates how to use the `Administrator` class to create and list tickets:

```java
Administrator admin = new Administrator("John Doe");
admin.createTicket("Printer Failure", "The printer is no longer working", Priority.HIGH, Service.HR, "John Doe", null);
admin.createTicket("Computer Failure", "The computer no longer works", Priority.MEDIUM, Service.ACCOUNTING, "Jane Doe", "Franck Gerard");

System.out.println(admin.listAllTickets());
```

## Technologies Used

- **Java 23**
- **Maven**
- **Lombok** (for reducing boilerplate code)
