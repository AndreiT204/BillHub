package billhub.repository.file;

import billhub.model.Provider;
import billhub.repository.ProviderRepository;

import java.io.*;
import java.util.*;

public class FileProviderRepository implements ProviderRepository {
    private final String filePath;
    public FileProviderRepository(String filePath) {
        this.filePath = filePath;
    }
    @Override public List<Provider> loadProviders() {
        List<Provider> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] p = line.split(",", 2);
                if (p.length != 2) continue;
                list.add(new Provider(p[0].trim(), p[1].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading providers: " + e.getMessage());
        }
        return list;
    }
}
