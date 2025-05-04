package com.logprocessor;
import java.util.Map;

public interface ILogAggregator {
    void add(Map<String, Object> log);
    Map<String, Object> getResults();
}