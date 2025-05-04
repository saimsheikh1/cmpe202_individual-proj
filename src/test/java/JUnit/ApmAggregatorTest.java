package JUnit;
import com.logprocessor.ApmAggregator;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;


public class ApmAggregatorTest {
    @Test
    public void testAddAndAggregate() {
        ApmAggregator aggregator = new ApmAggregator();
        Map<String, Object> log = new HashMap<>();
        log.put("metric", "cpu_usage");
        log.put("value", 50.0);
        aggregator.add(log);
        log.put("value", 70.0);
        aggregator.add(log);
        Map<String, Object> results = aggregator.getResults();
        assertTrue(results.containsKey("cpu_usage"));
    }
}
