package com.billhub.app;

import com.billhub.model.Bill;
import com.billhub.repository.BillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final BillRepository billRepository;

    public DataSeeder(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (billRepository.count() == 0) {


            billRepository.save(new Bill(150.00, LocalDate.now().plusDays(10), "Enel", "user"));
            billRepository.save(new Bill(45.50, LocalDate.now().plusDays(5), "Apa Nova", "user2"));
            billRepository.save(new Bill(80.00, LocalDate.now().minusDays(2), "Digi", "user3"));

            System.out.println("Test Data (Bills) Seeded Successfully!");
        }
    }
}