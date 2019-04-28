package net.random_development.monitor.service.mocks;

import net.random_development.monitor.data.InfluxService;
import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.MetricType;
import net.random_development.monitor.service.Influx;
import net.random_development.monitor.service.MetricService;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MetricServiceImpl implements MetricService {

    private final InfluxService influxService;

    @Autowired
    public MetricServiceImpl(InfluxService influxService) {
        this.influxService = influxService;
    }

    @Override
    public List<Metric> list(String resourceName) {
        String query = String.format("SHOW TAG VALUES FROM \"%s\" WITH KEY = \"%s\" WHERE %s = '%s'", Influx.MEASUREMENT, Influx.FIELD_METRIC_NAME, Influx.FIELD_RESOURCE_NAME, resourceName);
        return influxService.query(query).stream()
                .flatMap(this::toMetrics)
                .collect(Collectors.toList());
    }

    private Stream<Metric> toMetrics(QueryResult.Result result) {
        return Optional.ofNullable(result.getSeries())
                .map(Collection::stream)
                .orElse(Stream.empty())
                .flatMap(this::seriesToMetrics);
    }

    private Stream<Metric> seriesToMetrics(QueryResult.Series series) {
        return series.getValues().stream()
                .map(this::valueToMetric);
    }

    private Metric valueToMetric(List<Object> value) {
        Metric metric = new Metric();
        metric.setName((String) value.get(1));
        metric.setType(MetricType.NORMAL);
        return metric;
    }

    @Override
    public Optional<Metric> get(String resourceName, String metricName) {
        Metric metric = new Metric();
        metric.setName(metricName);
        metric.setType(MetricType.NORMAL);
        return Optional.of(metric);
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
