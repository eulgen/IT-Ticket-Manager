package com.codefromscratch.employee;

import com.codefromscratch.ticket.Priority;
import com.codefromscratch.ticket.Status;
import com.codefromscratch.ticket.Ticket;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Administrator extends Employee {
    public Administrator(String name) {
        super(name, Service.SUPPORT);
    }
}