import java.util.*;
import java.util.stream.Collectors;

/**
 * Aggregates request logs by URL, calculating response time percentiles and HTTP status code categories.
 */
public class RequestAggregator implements ILogAggregator {

    private static class RequestStats {
        List<Double> responseTimes = new ArrayList<>();
        Map<String, Integer> statusCodes = new HashMap<>(Map.of("2XX", 0, "4XX", 0, "5XX", 0));
    }

    private final Map<String, RequestStats> requests = new HashMap<>();

    @Override
    public void add(Map<String, Object> log) {
        String url = log.get("request_url").toString();
        double time = ((Number) log.get("response_time_ms")).doubleValue();
        int status = ((Number) log.get("response_status")).intValue();
        String category = categorizeStatusCode(status);

        requests.computeIfAbsent(url, k -> new RequestStats());
        RequestStats stats = requests.get(url);
        stats.responseTimes.add(time);
        stats.statusCodes.put(category, stats.statusCodes.get(category) + 1);
    }

    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new LinkedHashMap<>();

        for (Map.Entry<String, RequestStats> entry : requests.entrySet()) {
            String url = entry.getKey();
            RequestStats stats = entry.getValue();
            List<Double> times = stats.responseTimes.stream().sorted().collect(Collectors.toList());

            Map<String, Object> responseStats = new LinkedHashMap<>();
            responseStats.put("min", times.get(0));
            responseStats.put("50_percentile", percentile(times, 50));
            responseStats.put("90_percentile", percentile(times, 90));
            responseStats.put("95_percentile", percentile(times, 95));
            responseStats.put("99_percentile", percentile(times, 99));
            responseStats.put("max", times.get(times.size() - 1));

            Map<String, Object> logSummary = new LinkedHashMap<>();
            logSummary.put("response_times", responseStats);
            logSummary.put("status_codes", stats.statusCodes);

            results.put(url, logSummary);
        }

        return results;
    }

    private String categorizeStatusCode(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) return "2XX";
        if (statusCode >= 400 && statusCode < 500) return "4XX";
        if (statusCode >= 500 && statusCode < 600) return "5XX";
        return "Other";
    }

    private double percentile(List<Double> sorted, int p) {
        if (sorted.isEmpty()) return 0;
        double pos = p / 100.0 * (sorted.size() - 1);
        int lower = (int) Math.floor(pos);
        int upper = (int) Math.ceil(pos);
    
        if (lower == upper) {
            return sorted.get(lower);
        } else {
            double weight = pos - lower;
            return sorted.get(lower) * (1 - weight) + sorted.get(upper) * weight;
        }
    }
    
}
// This class aggregates request logs by URL, calculating response time percentiles and categorizing HTTP status codes. It implements the ILogAggregator interface and provides methods to add logs and retrieve aggregated results. The response times are stored in a list, and the status codes are categorized into 2XX, 4XX, and 5XX. The add method updates the statistics for each log entry, while the getResults method returns a summary of the aggregated results.