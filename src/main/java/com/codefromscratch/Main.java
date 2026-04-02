package com.codefromscratch;

import com.codefromscratch.employee.Administrator;
import com.codefromscratch.ticket.Priority;
import com.codefromscratch.employee.Service;
import com.codefromscratch.ticket.Status;
import com.codefromscratch.ticket.Ticket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        Administrator admin = new Administrator("John Doe");
        admin.createTicket("Imprimante defaillante","L'imprimante ne fonctionne plus",Priority.HIGH,Service.HR,"John Doe",null);
        admin.createTicket("Ordinateur defaillant","L'ordinateur ne fonctionne plus",Priority.MEDIUM,Service.ACCOUNTING,"Jane Doe","Franck Gerard");
        admin.createTicket("Probleme de connexion","Impossible de se connecter au serveur",Priority.LOW,Service.LOGISTICS,"Alice Smith","Martin Luck");
        System.out.println(admin.listAllTickets());
    }
}