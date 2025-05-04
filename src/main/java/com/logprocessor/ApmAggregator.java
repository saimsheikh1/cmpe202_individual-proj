package com.logprocessor;
import java.util.*;

public class ApmAggregator implements ILogAggregator {
    private final Map<String, List<Double>> metrics = new HashMap<>();

    public void add(Map<String, Object> log) {
        String metric = (String) log.get("metric");
        double value = ((Number) log.get("value")).doubleValue();
        metrics.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
    }

    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();
        for (String key : metrics.keySet()) {
            List<Double> values = metrics.get(key);
            Collections.sort(values);
            Map<String, Object> stats = new LinkedHashMap<>();
            stats.put("minimum", values.get(0));
            stats.put("median", median(values));
            stats.put("average", mean(values));
            stats.put("max", values.get(values.size() - 1));
            results.put(key, stats);
        }
        return results;
    }

    private double mean(List<Double> values) {
        return values.stream().mapToDouble(v -> v).average().orElse(0);
    }

    private double median(List<Double> values) {
        int size = values.size();
        return size % 2 == 0 ? (values.get(size / 2 - 1) + values.get(size / 2)) / 2 : values.get(size / 2);
    }
}