package com.logprocessor;
import java.util.Map;

public class LogClassifier {
    /**
     * Classifies logs into three categories: APM, application, and request.
     * 
     * @param log The log entry to classify.
     * @return The classification type as a string.
     */
    public String classify(Map<String, Object> log) {
        if (log.containsKey("metric") && log.containsKey("value")) return "apm";
        if (log.containsKey("level") && log.containsKey("message")) return "application";
        if (log.containsKey("request_method") && log.containsKey("request_url") &&
            log.containsKey("response_status") && log.containsKey("response_time_ms")) return "request";
        return null;
    }
}