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
@EnableScheduling
public class BillStatusScheduler {

    private final BillRepository billRepository;

    @Autowired
    public BillStatusScheduler(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOverdueBills() {
        System.out.println("[Thread Log] Checking for overdue bills...");

        List<Bill> allBills = billRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Bill bill : allBills) {
            if (!bill.isPaid() && bill.getDueDate().isBefore(today)) {
                System.out.println("ALERT: Bill ID " + bill.getId() + " is overdue!");
            }
        }
    }
}