public class LogAggregatorFactory {
    public ILogAggregator createAggregator(String type) {
        switch (type.toLowerCase()) {
            case "apm": return new ApmAggregator();
            case "application": return new ApplicationAggregator();
            case "request": return new RequestAggregator();
            default: throw new IllegalArgumentException("Unsupported log type: " + type);
        }
    }
}