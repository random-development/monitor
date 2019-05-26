package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;

import java.util.List;
import java.util.Optional;

public interface ComplexMetricService {

    Metric create(String resourceName, Metric metric);

    Optional<Metric> get(String resourceName, String metricName);

    List<Metric> list(String resourceName);

    void delete(String resourceName, String metricName);
}
