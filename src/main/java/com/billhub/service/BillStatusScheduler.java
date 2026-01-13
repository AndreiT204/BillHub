package com.billhub.service;

import com.billhub.model.Bill;
import com.billhub.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling // This turns on the "Automatic Timer" feature
public class BillStatusScheduler {

    private final BillRepository billRepository;

    @Autowired
    public BillStatusScheduler(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    // This method runs automatically every 60 seconds (60000 milliseconds)
    @Scheduled(fixedRate = 60000)
    public void checkOverdueBills() {
        System.out.println("[Thread Log] Checking for overdue bills...");

        List<Bill> allBills = billRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Bill bill : allBills) {
            // Logic: If bill is NOT paid AND due date is in the past
            if (!bill.isPaid() && bill.getDueDate().isBefore(today)) {
                System.out.println("ALERT: Bill ID " + bill.getId() + " is overdue!");
                // Here you could add logic to send an email or add a fine
            }
        }
    }
}