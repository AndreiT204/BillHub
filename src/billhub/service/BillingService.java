package billhub.service;

import billhub.exception.InvalidBillDataException;
import billhub.exception.ProviderNotFoundException;
import billhub.model.Bill;
import billhub.model.Notice;
import billhub.model.Provider;
import billhub.repository.BillRepository;
import billhub.repository.NoticeRepository;
import billhub.repository.ProviderRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BillingService {
    private final BillRepository billRepo;
    private final ProviderRepository providerRepo;
    private final NoticeRepository noticeRepo;

    private List<Bill> bills;
    private List<Provider> providers;
    private List<Notice> notices;

    public BillingService(BillRepository billRepo,
                          ProviderRepository providerRepo,
                          NoticeRepository noticeRepo) {
        this.billRepo = billRepo;
        this.providerRepo = providerRepo;
        this.noticeRepo = noticeRepo;

        this.bills = billRepo.loadBills();
        this.providers = providerRepo.loadProviders();
        this.notices = noticeRepo.loadNotices();
    }

    public void listAllBills() {
        if (bills.isEmpty()) {
            System.out.println("No bills.");
            return;
        }
        bills.forEach(System.out::println);
    }

    public void listUnpaidBills() {
        List<Bill> unpaid = bills.stream().filter(b -> !b.isPaid()).collect(Collectors.toList());
        if (unpaid.isEmpty()) {
            System.out.println("All bills are paid.");
            return;
        }
        unpaid.forEach(System.out::println);
    }

    public void listProviders() {
        if (providers.isEmpty()) {
            System.out.println("No providers.");
            return;
        }
        providers.forEach(System.out::println);
    }

    public void listNotices() {
        if (notices.isEmpty()) {
            System.out.println("No notices.");
            return;
        }
        notices.forEach(System.out::println);
    }

    public void markPaid(String id) throws InvalidBillDataException {
        Bill found = bills.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
        if (id == null || id.isBlank()) throw new InvalidBillDataException("ID cannot be empty.");
        if (!id.matches("\\d+")) {
            throw new InvalidBillDataException("ID must be a numeric value.");
        }
        if (found == null) throw new InvalidBillDataException("Bill id not found: " + id);
        if (found.isPaid()) {
            System.out.println("Already paid.");
            return;
        }
        found.markAsPaid();
        billRepo.saveBills(bills);
        System.out.println("Marked as PAID.");
    }

    public void addBill(String id, String providerName, String amountStr, String dueDate)
            throws InvalidBillDataException, ProviderNotFoundException {

        if (id == null || id.isBlank()) throw new InvalidBillDataException("ID cannot be empty.");
        if (bills.stream().anyMatch(b -> b.getId().equals(id)))
            throw new InvalidBillDataException("ID already exists.");

        if (providers.stream().noneMatch(p -> p.getName().equalsIgnoreCase(providerName)))
            throw new ProviderNotFoundException(providerName);

        double amount;
        try { amount = Double.parseDouble(amountStr); }
        catch (NumberFormatException e) { throw new InvalidBillDataException("Amount must be a number."); }
        if (amount <= 0) throw new InvalidBillDataException("Amount must be positive.");

        try {LocalDate.parse(dueDate);}
        catch (DateTimeParseException e) {
            throw new InvalidBillDataException("Due date must be in format YYYY-MM-DD.");
        }

        Bill b = new Bill(id, providerName, amount, dueDate, false);
        bills.add(b);
        billRepo.saveBills(bills);
        System.out.println("Bill added.");
    }
}
