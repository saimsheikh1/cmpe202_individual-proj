# Individual Project for CMPE 202 Software Systems

# Log Aggregator CLI Tool

## About

This is a Java-based command-line application that parses a mixed `.txt` log file, classifies log entries into different types (APM, Application, and Request), performs aggregation based on type, and writes the results to structured JSON files.

The tool is extensible and uses the **Factory Design Pattern** to easily support new types of log aggregators.

---

## How to Run the Project

### 1. Clone or Download
git clone https://github.com/yourusername/log-aggregator-java.git
cd log-aggregator-java

### 2. Requirements
Ensure you're using Java SDK 17+ and Gradle.

### 3. Run commands in terminal
gradle build
gradle run --args="--file logs.txt"
This will:
  1. Parse logs.txt
  2. Classify logs
  3. Generate output files: apm.json, application.json, request.json 

  apm.json: Aggregated APM metrics (e.g., CPU, memory)
  application.json: Count of log entries by level (INFO, ERROR, etc.)
  request.json: Response time percentiles and HTTP status code breakdown by URL
