package JUnit;

import com.logprocessor.Main;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testMainRunsWithoutCrash() throws IOException {
        String testLog =
        "metric=cpu_usage_percent value=75\n" +
        "level=INFO message=\"Service started\"\n" +
        "request_method=GET request_url=/api response_status=200 response_time_ms=100\n";
    

        File tempLog = File.createTempFile("test-log", ".txt");
        Files.write(tempLog.toPath(), testLog.getBytes());

        String[] args = {"--file", tempLog.getAbsolutePath()};
        Main.main(args);

        assertTrue(new File("apm.json").exists());
        assertTrue(new File("application.json").exists());
        assertTrue(new File("request.json").exists());

        tempLog.deleteOnExit();
    }
}
