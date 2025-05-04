package JUnit;

import com.logprocessor.RequestAggregator;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RequestAggregatorTest {

    @Test
    public void testAddAndGetResults() {
        RequestAggregator aggregator = new RequestAggregator();

        Map<String, Object> log1 = new HashMap<>();
        log1.put("request_url", "/api/test");
        log1.put("response_status", 200);
        log1.put("response_time_ms", 150.0);
        aggregator.add(log1);

        Map<String, Object> log2 = new HashMap<>();
        log2.put("request_url", "/api/test");
        log2.put("response_status", 404);
        log2.put("response_time_ms", 250.0);
        aggregator.add(log2);

        Map<String, Object> results = aggregator.getResults();
        assertTrue(results.containsKey("/api/test"));

        Map<String, Object> responseData = (Map<String, Object>) results.get("/api/test");
        assertNotNull(responseData.get("response_times"));
        assertNotNull(responseData.get("status_codes"));
    }
}
