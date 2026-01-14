package com.billhub;

import com.billhub.model.Bill;
import com.billhub.model.Notice;
import com.billhub.model.User;
import com.billhub.model.Provider;
import com.billhub.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BillingServiceTest {

    @Autowired
    private BillRepository billRepository;

    @Test
    void testBillConstructor() {
        System.out.println("TEST 1 START: Testing Bill Constructor...");

        Bill bill = new Bill(100.0, LocalDate.now(), "TestProvider", "TestUser");

        assertNotNull(bill);
        assertEquals(100.0, bill.getAmount());
        assertEquals("TestProvider", bill.getProvider());
        assertEquals("TestUser", bill.getUserUsername());
        assertFalse(bill.isPaid());

        System.out.println("TEST 1 PASSED: Bill Constructor works.\n");
    }

    @Test
    void testUserConstructor() {
        System.out.println("TEST 2 START: Testing User Constructor...");

        User user = new User("AlphaUser", "SecretPass", "USER");

        assertNotNull(user);
        assertEquals("AlphaUser", user.getUsername());
        assertEquals("SecretPass", user.getPassword());
        assertEquals("USER", user.getRole());

        System.out.println("TEST 2 PASSED: User Constructor works.\n");
    }

    @Test
    void testProviderConstructor() {
        System.out.println("TEST 3 START: Testing Provider Constructor...");

        // FIX: Ensured both strings match exactly
        Provider provider = new Provider("Retim", "Waste Management");

        assertNotNull(provider);
        assertEquals("Retim", provider.getName());
        assertEquals("Waste Management", provider.getType());

        System.out.println("TEST 3 PASSED: Provider Constructor works.\n");
    }

    @Test
    void testNoticeConstructor() {
        System.out.println("TEST 4 START: Testing Notice Constructor...");

        Notice notice = new Notice("Power Outage Scheduled", true, "Enel");

        assertNotNull(notice);
        assertEquals("Power Outage Scheduled", notice.getMessage());
        assertTrue(notice.isCritical());
        assertEquals("Enel", notice.getProvider());

        System.out.println("TEST 4 PASSED: Notice Constructor works.\n");
    }

    @Test
    void testCreateBillFunctionality() {
        System.out.println("TEST 5 START: Testing Database Insert (Create Bill)...");

        Bill newBill = new Bill(50.50, LocalDate.now().plusDays(5), "Digi", "Andrei");
        Bill savedBill = billRepository.save(newBill);

        assertNotNull(savedBill.getId());
        System.out.println("   -> Generated ID: " + savedBill.getId());

        System.out.println("TEST 5 PASSED: Bill saved to Database successfully.\n");
    }

    @Test
    void testFindBillsByUser() {
        System.out.println("TEST 6 START: Testing 'Find Bills by User'...");

        billRepository.save(new Bill(200.0, LocalDate.now(), "Enel", "UniqueUser"));

        List<Bill> bills = billRepository.findByUserUsername("UniqueUser");

        assertFalse(bills.isEmpty());
        assertEquals("Enel", bills.get(0).getProvider());

        System.out.println("   -> Found " + bills.size() + " bills for user 'UniqueUser'.");
        System.out.println("TEST 6 PASSED: Successfully retrieved user bills.\n");
    }

    @Test
    void testPayBillFunctionality() {
        System.out.println("TEST 7 START: Testing 'Pay Bill' Logic...");

        Bill bill = billRepository.save(new Bill(75.0, LocalDate.now(), "Apa Nova", "Maria"));

        bill.setPaid(true);
        Bill updatedBill = billRepository.save(bill);

        assertTrue(updatedBill.isPaid());
        System.out.println("   -> Bill Status is now: " + (updatedBill.isPaid() ? "PAID" : "UNPAID"));

        System.out.println("TEST 7 PASSED: Bill status updated successfully.\n");
    }
}