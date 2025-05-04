package JUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import com.logprocessor.LogParser;
public class LogParserTest {
    @Test
    public void testParseValidLogLine() {
        LogParser parser = new LogParser("testfile.txt");
        String line = "timestamp=2024-01-01T00:00:00Z metric=cpu_usage_percent value=75";
        Map<String, Object> result = parser.parseLine(line);
        assertEquals("cpu_usage_percent", result.get("metric"));
        assertEquals(75.0, result.get("value"));
    }

    @Test
    public void testParseInvalidLogLineReturnsNull() {
        LogParser parser = new LogParser("testfile.txt");
        String line = "";
        assertNull(parser.parseLine(line));
    }
}
