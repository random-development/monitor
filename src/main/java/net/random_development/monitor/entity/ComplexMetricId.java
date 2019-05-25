package net.random_development.monitor.entity;

import java.io.Serializable;

public class ComplexMetricId implements Serializable {
    private String name;
    private String sourceResource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceResource() {
        return sourceResource;
    }

    public void setSourceResource(String sourceResource) {
        this.sourceResource = sourceResource;
    }
}
