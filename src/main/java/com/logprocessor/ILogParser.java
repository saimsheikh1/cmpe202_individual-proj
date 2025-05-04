package com.logprocessor;
import java.util.Map;

public interface ILogParser {
    Map<String, Object> parseLine(String line);
}