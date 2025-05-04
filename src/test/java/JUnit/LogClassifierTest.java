package JUnit;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import com.logprocessor.LogClassifier;

public class LogClassifierTest {
    @Test
    public void testClassifyApmLog() {
        LogClassifier classifier = new LogClassifier();
        Map<String, Object> log = new HashMap<>();
        log.put("metric", "cpu");
        log.put("value", 50);
        assertEquals("apm", classifier.classify(log));
    }

    @Test
    public void testClassifyApplicationLog() {
        LogClassifier classifier = new LogClassifier();
        Map<String, Object> log = new HashMap<>();
        log.put("level", "INFO");
        log.put("message", "Test message");
        assertEquals("application", classifier.classify(log));
    }

    @Test
    public void testClassifyRequestLog() {
        LogClassifier classifier = new LogClassifier();
        Map<String, Object> log = new HashMap<>();
        log.put("request_method", "GET");
        log.put("request_url", "/api");
        log.put("response_status", 200);
        log.put("response_time_ms", 123);
        assertEquals("request", classifier.classify(log));
    }

    @Test
    public void testUnknownLogReturnsNull() {
        LogClassifier classifier = new LogClassifier();
        assertNull(classifier.classify(new HashMap<>()));
    }
}
