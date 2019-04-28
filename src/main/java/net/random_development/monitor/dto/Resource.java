package net.random_development.monitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Resource {

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Metric> metrics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }
}
