package net.random_development.monitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Metric {

    private String name;
    private MetricType type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sourceMetric;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double period;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double interval;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceMetric() {
        return sourceMetric;
    }

    public void setSourceMetric(String sourceMetric) {
        this.sourceMetric = sourceMetric;
    }

    public Double getPeriod() {
        return period;
    }

    public void setPeriod(Double period) {
        this.period = period;
    }

    public Double getInterval() {
        return interval;
    }

    public void setInterval(Double interval) {
        this.interval = interval;
    }
}
