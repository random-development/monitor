package net.random_development.monitor.dto;

public class Metric {

    private String name;
    private MetricType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetricType getType() {
        return type;
    }

    public void setType(MetricType type) {
        this.type = type;
    }
}
