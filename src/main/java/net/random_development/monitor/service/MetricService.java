package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;

import java.util.List;

public interface MetricService {

    List<Metric> list(String resourceName);

    Metric get(String resourceName, String metricName);

    Metric create(Metric metric);

}
