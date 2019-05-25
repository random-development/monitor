package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;

public interface ComplexMetricService {

    Metric create(String resourceName, Metric metric);

}
