package billhub.app;

import billhub.exception.InvalidBillDataException;
import billhub.exception.ProviderNotFoundException;
import billhub.repository.file.FileBillRepository;
import billhub.repository.file.FileNoticeRepository;
import billhub.repository.file.FileProviderRepository;
import billhub.service.BillingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String base = "data/";
        BillingService service = new BillingService(
                new FileBillRepository(base + "bills.txt"),
                new FileProviderRepository(base + "providers.txt"),
                new FileNoticeRepository(base + "notices.txt")
        );

        // === Command-Line Mode ===
        if (args.length > 0) {
            String cmd = args[0].toLowerCase();
            try {
                switch (cmd) {
                    case "list" -> {
                        System.out.println("Available commands:");
                        System.out.println("  bills        - List all bills");
                        System.out.println("  unpaid       - List unpaid bills");
                        System.out.println("  pay <id>     - Mark a bill as PAID");
                        System.out.println("  addbill      - Add a new bill interactively");
                        System.out.println("  provider     - List all providers");
                        System.out.println("  notices      - View all notices");
                        System.out.println("  list         - Show this list");
                    }
                    case "bills" -> service.listAllBills();
                    case "unpaid" -> service.listUnpaidBills();
                    case "pay" -> {
                        if (args.length < 2) {
                            System.out.println("Usage: java billhub.app.Main pay <billId>");
                        } else {
                            service.markPaid(args[1]);
                        }
                    }
                    case "addbill" -> {
                        Scanner sc = new Scanner(System.in);
                        System.out.print("New ID: ");
                        String id = sc.nextLine().trim();
                        System.out.print("Provider name: ");
                        String prov = sc.nextLine().trim();
                        System.out.print("Amount: ");
                        String amount = sc.nextLine().trim();
                        System.out.print("Due date (YYYY-MM-DD): ");
                        String due = sc.nextLine().trim();
                        service.addBill(id, prov, amount, due);
                    }
                    case "provider" -> service.listProviders();
                    case "notices" -> service.listNotices();
                    default -> System.out.println("Unknown command. Use 'list' to see all options.");
                }
            } catch (InvalidBillDataException | ProviderNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return;
        }

/*        // === Interactive Menu Mode ===
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Bill Hub (Midterm) ===");
            System.out.println("1. List all bills");
            System.out.println("2. List unpaid bills");
            System.out.println("3. Mark bill as PAID");
            System.out.println("4. Add new bill");
            System.out.println("5. List providers");
            System.out.println("6. View notices");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> service.listAllBills();
                    case "2" -> service.listUnpaidBills();
                    case "3" -> {
                        System.out.print("Bill ID: ");
                        service.markPaid(sc.nextLine().trim());
                    }
                    case "4" -> {
                        System.out.print("New ID: ");
                        String id = sc.nextLine().trim();
                        System.out.print("Provider name: ");
                        String prov = sc.nextLine().trim();
                        System.out.print("Amount: ");
                        String amount = sc.nextLine().trim();
                        System.out.print("Due date (YYYY-MM-DD): ");
                        String due = sc.nextLine().trim();
                        service.addBill(id, prov, amount, due);
                    }
                    case "5" -> service.listProviders();
                    case "6" -> service.listNotices();
                    case "0" -> { System.out.println("Bye!"); return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (InvalidBillDataException | ProviderNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }*/
    }
}
