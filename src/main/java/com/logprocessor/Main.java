package com.logprocessor;
// import com.logprocessor.LogClassifier;
// import com.logprocessor.OutputWriter;
// import com.logprocessor.LogAggregatorFactory;
// import com.logprocessor.LogParser;
// import com.logprocessor.ILogAggregator;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].equals("--file") || args.length < 2) {
            System.err.println("Usage: java Main --file <filename.txt>");
            System.exit(1);
        }

        String filename = args[1];
        LogParser parser = new LogParser(filename);
        LogClassifier classifier = new LogClassifier();
        LogAggregatorFactory factory = new LogAggregatorFactory();

        ILogAggregator apmAgg = factory.createAggregator("apm");
        ILogAggregator appAgg = factory.createAggregator("application");
        ILogAggregator reqAgg = factory.createAggregator("request");

        int processed = 0;
        try {
            List<String> lines = parser.readLines();
            for (String line : lines) {
                Map<String, Object> parsed = parser.parseLine(line);
                if (parsed == null) continue;

                String type = classifier.classify(parsed);
                switch (type) {
                    case "apm": apmAgg.add(parsed); break;
                    case "application": appAgg.add(parsed); break;
                    case "request": reqAgg.add(parsed); break;
                    default: break;
                }
                processed++;
            }
        } catch (IOException e) {
            System.err.println("Failed to read the log file: " + e.getMessage());
        }

        System.out.println("Processed " + processed + " log lines.");

        OutputWriter writer = new OutputWriter();
        writer.write("apm.json", apmAgg.getResults());
        writer.write("application.json", appAgg.getResults());
        writer.write("request.json", reqAgg.getResults());
    }
}