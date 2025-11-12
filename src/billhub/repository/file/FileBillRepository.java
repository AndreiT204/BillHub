package billhub.repository.file;

import billhub.model.Bill;
import billhub.repository.BillRepository;

import java.io.*;
import java.util.*;

public class FileBillRepository implements BillRepository {
    private final String filePath;
    public FileBillRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override public List<Bill> loadBills() {
        List<Bill> bills = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return bills;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] p = line.split(",", -1);
                if (p.length != 5) continue;
                bills.add(new Bill(p[0].trim(), p[1].trim(),
                        Double.parseDouble(p[2].trim()),
                        p[3].trim(),
                        Boolean.parseBoolean(p[4].trim())));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading bills: " + e.getMessage());
        }
        return bills;
    }
    @Override public void saveBills(List<Bill> bills) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Bill b : bills) {
                bw.write(b.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bills: " + e.getMessage());
        }
    }
}
