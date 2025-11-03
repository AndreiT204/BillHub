package billhub.repository;

import billhub.model.Notice;
import java.util.List;

public interface NoticeRepository {
    List<Notice> loadNotices();
}
