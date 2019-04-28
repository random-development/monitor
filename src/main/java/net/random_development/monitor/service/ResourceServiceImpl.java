package net.random_development.monitor.service;

import net.random_development.monitor.data.InfluxService;
import net.random_development.monitor.dto.Resource;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final MetricService metricService;
    private final InfluxService influxService;

    @Autowired
    public ResourceServiceImpl(MetricService metricService,
                               InfluxService influxService) {
        this.metricService = metricService;
        this.influxService = influxService;
    }

    @Override
    public List<Resource> list() {
        String query = String.format("SHOW TAG VALUES FROM \"%s\" WITH KEY = \"%s\"", Influx.MEASUREMENT, Influx.FIELD_RESOURCE_NAME);
        return influxService.query(query).stream()
                .flatMap(this::toResources)
                .collect(Collectors.toList());
    }

    private Stream<Resource> toResources(QueryResult.Result result) {
        return Optional.ofNullable(result.getSeries())
                .map(Collection::stream)
                .orElse(Stream.empty())
                .flatMap(this::seriesToResources);
    }

    private Stream<Resource> seriesToResources(QueryResult.Series series) {
        return series.getValues().stream()
                .map(this::valueToResource);
    }

    private Resource valueToResource(List<Object> value) {
        Resource resource = new Resource();
        resource.setName((String) value.get(1));
        return resource;
    }

    @Override
    public Optional<Resource> get(String resourceName) {
        Resource resource = new Resource();
        resource.setName(resourceName);
        resource.setMetrics(metricService.list(resourceName));
        return Optional.of(resource);
    }

}
