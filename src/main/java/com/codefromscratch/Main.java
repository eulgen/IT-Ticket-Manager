package com.codefromscratch;

import com.codefromscratch.employee.Administrator;
import com.codefromscratch.ticket.*;
import com.codefromscratch.employee.Service;

import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TicketManager ticketManager = new TicketManager();
        ticketManager.createTicket("Test", "Test", Priority.LOW, Service.SUPPORT, "Test", "Test");
        System.out.println(ticketManager.listAllTickets());
    }
}