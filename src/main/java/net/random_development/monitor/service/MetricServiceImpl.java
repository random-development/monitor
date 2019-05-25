package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetricServiceImpl implements MetricService {

    private final ComplexMetricService complexMetricService;
    private final NormalMetricService normalMetricService;

    @Autowired
    public MetricServiceImpl(ComplexMetricService complexMetricService,
                             NormalMetricService normalMetricService) {
        this.complexMetricService = complexMetricService;
        this.normalMetricService = normalMetricService;
    }

    @Override
    public List<Metric> list(String resourceName) {
        List<Metric> metrics = complexMetricService.list(resourceName);

        normalMetricService.list(resourceName).stream()
                .forEach(metric -> {
                    boolean exists = metrics.stream()
                            .anyMatch(m -> metric.getName().equals(m.getName()));
                    if (!exists) {
                        metrics.add(metric);
                    }
                });

        return metrics;
    }

    @Override
    public Optional<Metric> get(String resourceName, String metricName) {
        Optional<Metric> complexMetric = complexMetricService.get(resourceName, metricName);
        if (complexMetric.isPresent()) {
            return complexMetric;
        }
        return normalMetricService.get(resourceName, metricName);
    }

    @Override
    public Metric create(String resourceName, Metric metric) {
        return this.complexMetricService.create(resourceName, metric);
    }

}
