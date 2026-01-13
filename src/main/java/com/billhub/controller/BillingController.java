package com.billhub.controller;

import com.billhub.model.Bill;
import com.billhub.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:3000")
public class BillingController {

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/{username}")
    public List<Bill> getBills(@PathVariable String username) {
        return billRepository.findByUserUsername(username);
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        return billRepository.save(bill);
    }

    @PostMapping("/{id}/pay")
    public Bill payBill(@PathVariable Long id) {
        return billRepository.findById(id).map(bill -> {
            bill.setPaid(true);
            return billRepository.save(bill);
        }).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billRepository.deleteById(id);
    }
}