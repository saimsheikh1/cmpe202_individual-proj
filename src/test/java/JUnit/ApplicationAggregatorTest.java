package JUnit;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import com.logprocessor.ApplicationAggregator;

public class ApplicationAggregatorTest {
    @Test
    public void testAddAndGetResults() {
        ApplicationAggregator aggregator = new ApplicationAggregator();
        Map<String, Object> log = new HashMap<>();
        log.put("level", "INFO");
        aggregator.add(log);
        Map<String, Object> results = aggregator.getResults();
        assertEquals(1, results.get("INFO"));
    }
}
