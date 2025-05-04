import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Aggregates application logs by counting occurrences of severity levels (e.g., ERROR, INFO).
 */
public class ApplicationAggregator implements ILogAggregator {
    private final Map<String, Integer> levelCounts = new HashMap<>();

    @Override
    public void add(Map<String, Object> log) {
        if (log.containsKey("level")) {
            String level = log.get("level").toString().toUpperCase(Locale.ROOT);
            levelCounts.put(level, levelCounts.getOrDefault(level, 0) + 1);
        }
    }

    @Override
    public Map<String, Object> getResults() {
        return new HashMap<>(levelCounts);
    }
}
// This class aggregates application logs by counting occurrences of severity levels (e.g., ERROR, INFO). It implements the ILogAggregator interface and provides methods to add logs and retrieve aggregated results. The level counts are stored in a HashMap, where the keys are the severity levels and the values are the counts. The add method updates the count for each log entry, while the getResults method returns a copy of the aggregated results.