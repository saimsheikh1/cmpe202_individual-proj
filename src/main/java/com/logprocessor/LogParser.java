package com.logprocessor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class LogParser implements ILogParser {
    private final String filename;

    public LogParser(String filename) {
        this.filename = filename;
    }

    public List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) lines.add(line);
        reader.close();
        return lines;
    }

    public Map<String, Object> parseLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        Map<String, Object> parsed = new HashMap<>();
        Pattern pattern = Pattern.compile("([a-zA-Z_]+)=(\\S+|\"[^\"]*\")");

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String key = matcher.group(1);
            String rawValue = matcher.group(2).replaceAll("^\"|\"$", "");
            try {
                double num = Double.parseDouble(rawValue);
                parsed.put(key, num);
            } catch (NumberFormatException e) {
                parsed.put(key, rawValue);
            }
        }
        return parsed.size() > 0 ? parsed : null;
    }
}