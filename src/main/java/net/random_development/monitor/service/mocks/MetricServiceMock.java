package net.random_development.monitor.service.mocks;

import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.MetricType;
import net.random_development.monitor.service.MetricService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricServiceMock implements MetricService {

    @Override
    public List<Metric> list(String resourceName) {
        List<Metric> metrics = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            metrics.add(mockMetric(j));
        }
        return metrics;
    }

    @Override
    public Metric get(String resourceName, String metricName) {
        return mockMetric(0);
    }

    @Override
    public Metric create(Metric metric) {
        return mockMetric(0);
    }

    private Metric mockMetric(int i) {
        Metric metric = new Metric();
        metric.setName(String.format("metric-%s", i));
        metric.setType(MetricType.NORMAL);
        return metric;
    }

}
