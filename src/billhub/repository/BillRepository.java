package billhub.repository;

import billhub.model.Bill;
import java.util.List;

public interface BillRepository {
    List<Bill> loadBills();
    void saveBills(List<Bill> bills);
}
