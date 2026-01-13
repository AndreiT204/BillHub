package com.billhub.service;

import com.billhub.model.*;
import com.billhub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final BillRepository billRepo;
    private final ProviderRepository providerRepo;
    private final NoticeRepository noticeRepo;

    @Autowired
    public BillingService(BillRepository billRepo, ProviderRepository providerRepo, NoticeRepository noticeRepo) {
        this.billRepo = billRepo;
        this.providerRepo = providerRepo;
        this.noticeRepo = noticeRepo;
    }

    public List<Bill> getAllBills() {
        return billRepo.findAll();
    }

    public List<Provider> getAllProviders() {
        return providerRepo.findAll();
    }

    public List<Notice> getAllNotices() {
        return noticeRepo.findAll();
    }

    public Bill createBill(Bill bill) {
        if (bill.getAmount() < 0) {
            throw new IllegalArgumentException("Bill amount cannot be negative");
        }
        return billRepo.save(bill); // Saves to Database
    }

    public Bill payBill(Long billId) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Error: Bill with ID " + billId + " not found."));

        if (bill.isPaid()) {
            throw new IllegalStateException("Error: Bill is already paid.");
        }

        bill.setPaid(true);
        return billRepo.save(bill);
    }

}