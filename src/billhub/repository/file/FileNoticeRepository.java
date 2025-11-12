package billhub.repository.file;

import billhub.model.Notice;
import billhub.repository.NoticeRepository;

import java.io.*;
import java.util.*;


public class FileNoticeRepository implements NoticeRepository {
    private final String filePath;
    public FileNoticeRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override public List<Notice> loadNotices() {
        List<Notice> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] p = line.split(",", 5);
                if (p.length != 5) continue;
                list.add(new Notice(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading notices: " + e.getMessage());
        }
        return list;
    }
}
