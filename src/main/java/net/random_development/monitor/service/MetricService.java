package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;

import java.util.List;
import java.util.Optional;

public interface MetricService {

    List<Metric> list(String resourceName);

    Optional<Metric> get(String resourceName, String metricName);

    Metric create(String resourceName, Metric metric);

    void delete(String resourceName, String metricName);
}
