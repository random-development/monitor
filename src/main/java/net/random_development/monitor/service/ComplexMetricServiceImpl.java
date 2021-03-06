package net.random_development.monitor.service;

import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.MetricType;
import net.random_development.monitor.entity.ComplexMetric;
import net.random_development.monitor.exception.BadRequestApiException;
import net.random_development.monitor.exception.NotFoundApiException;
import net.random_development.monitor.repository.ComplexMetricRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ComplexMetricServiceImpl implements ComplexMetricService {

    private static final Logger LOGGER = Logger.getLogger(MeasurementServiceImpl.class.getName());

    private final ComplexMetricRepository complexMetricRepository;

    @Autowired
    public ComplexMetricServiceImpl(ComplexMetricRepository complexMetricRepository) {
        this.complexMetricRepository = complexMetricRepository;
    }

    @Override
    public Metric create(String resourceName, Metric metric) {
        if (!MetricType.COMPLEX.equals(metric.getType())) {
            throw new BadRequestApiException("You can only create complex metrics.");
        }
        if (metric.getPeriod() == null || metric.getPeriod() < 1) {
            throw new BadRequestApiException("Period needs to be at least 1.");
        }
        if (metric.getInterval() == null || metric.getInterval() < 1) {
            throw new BadRequestApiException("Interval needs to be at least 1.");
        }
        if (metric.getPeriod() % metric.getInterval() != 0) {
            throw new BadRequestApiException("Period must be divisible by interval.");
        }
        if (Strings.isEmpty(metric.getName())) {
            throw new BadRequestApiException("Name missing.");
        }
        if (Strings.isEmpty(metric.getSourceMetric())) {
            throw new BadRequestApiException("Source metric missing.");
        }

        ComplexMetric complexMetric = toComplexMetric(resourceName, metric);
        LOGGER.log(Level.FINE, "creating or updating: {0}", new Object[]{complexMetric});
        return toMetric(complexMetricRepository.save(complexMetric));
    }

    @Override
    public Optional<Metric> get(String resourceName, String metricName) {
        return complexMetricRepository.getBySourceResourceAndName(resourceName, metricName)
                .map(this::toMetric);
    }

    @Override
    public List<Metric> list(String resourceName) {
        return complexMetricRepository.findBySourceResource(resourceName).stream()
                .map(this::toMetric)
                .collect(Collectors.toList());
    }

    private Metric toMetric(ComplexMetric complexMetric) {
        Metric metric = new Metric();
        metric.setType(MetricType.COMPLEX);
        metric.setName(complexMetric.getName());
        metric.setInterval(complexMetric.getInterval());
        metric.setPeriod(complexMetric.getPeriod());
        metric.setUserId(complexMetric.getUserId());
        metric.setSourceMetric(complexMetric.getSourceMetric());
        return metric;
    }

    private ComplexMetric toComplexMetric(String resourceName, Metric metric) {
        ComplexMetric complexMetric = new ComplexMetric();
        complexMetric.setName(metric.getName());
        complexMetric.setSourceMetric(metric.getSourceMetric());
        complexMetric.setSourceResource(resourceName);
        complexMetric.setPeriod(metric.getPeriod());
        complexMetric.setInterval(metric.getInterval());
        complexMetric.setUserId(metric.getUserId());
        return complexMetric;
    }

    @Override
    public void delete(String resourceName, String metricName) {
        int rows = complexMetricRepository.deleteBySourceResourceAndName(resourceName, metricName);
        if (rows != 1) {
            throw new NotFoundApiException();
        }
    }

}
