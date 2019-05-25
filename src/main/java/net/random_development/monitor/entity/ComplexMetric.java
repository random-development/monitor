package net.random_development.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;



@Entity
@IdClass(ComplexMetricId.class)
public class ComplexMetric {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "source_resource", nullable = false)
    private String sourceResource;

    @Column(name = "source_metric", nullable = false)
    private String sourceMetric;

    @Column(name = "metric_period", nullable = false)
    private Double period;

    @Column(name = "metric_interval", nullable = false)
    private Double interval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceResource() {
        return sourceResource;
    }

    public void setSourceResource(String sourceResource) {
        this.sourceResource = sourceResource;
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

    @Override
    public String toString() {
        return "ComplexMetric{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", sourceResource='" + sourceResource + '\'' +
                ", sourceMetric='" + sourceMetric + '\'' +
                ", period=" + period +
                ", interval=" + interval +
                '}';
    }
}
