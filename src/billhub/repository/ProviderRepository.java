package billhub.repository;

import billhub.model.Provider;
import java.util.List;

public interface ProviderRepository {
    List<Provider> loadProviders();
}
