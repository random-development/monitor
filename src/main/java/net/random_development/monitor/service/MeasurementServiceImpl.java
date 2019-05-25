package net.random_development.monitor.service;

import net.random_development.monitor.data.InfluxService;
import net.random_development.monitor.dto.ListMeasurementsParameters;
import net.random_development.monitor.dto.Measurement;
import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.QueryWhereElement;
import net.random_development.monitor.exception.NotImplementedApiException;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private static final Logger LOGGER = Logger.getLogger(MeasurementServiceImpl.class.getName());

    private final InfluxService influxService;
    private final DateParsingService dateParsingService;
    private final MetricService metricService;

    @Autowired
    public MeasurementServiceImpl(InfluxService influxService,
                                  DateParsingService dateParsingService,
                                  MetricService metricService) {
        this.influxService = influxService;
        this.dateParsingService = dateParsingService;
        this.metricService = metricService;
    }

    @Override
    public List<Measurement> list(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        return metricService.get(resourceName, metricName)
                .map(metric -> {
                    switch (metric.getType()) {
                        case COMPLEX:
                            return listComplex(resourceName, metric, listMeasurementsParameters);
                        case NORMAL:
                            return listNormal(resourceName, metricName, listMeasurementsParameters);
                        default:
                            throw new NotImplementedApiException();
                    }
                })
                .orElse(Collections.emptyList());
    }

    private List<Measurement> listComplex(String resourceName, Metric metric, ListMeasurementsParameters listMeasurementsParameters) {
        String query = buildComplexListQuery(resourceName, metric, listMeasurementsParameters);
        return influxService.query(query).stream()
                .flatMap(this::toMeasurements)
                .collect(Collectors.toList());
    }

    private List<Measurement> listNormal(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        String query = buildListQuery(resourceName, metricName, listMeasurementsParameters);
        return influxService.query(query).stream()
                .flatMap(this::toMeasurements)
                .collect(Collectors.toList());
    }

    private String buildComplexListQuery(String resourceName, Metric metric, ListMeasurementsParameters listMeasurementsParameters) {
        List<String> query = new ArrayList<>();
        int n = metric.getPeriod() / metric.getInterval();
        query.add(String.format("SELECT moving_average(sum(%s), %d) FROM \"%s\"", Influx.FIELD_VALUE, n, Influx.MEASUREMENT));
        query.add(String.format("WHERE %s", buildQueryWhere(resourceName, metric.getSourceMetric(), listMeasurementsParameters)));
        query.add(String.format("GROUP BY time(%ds)", metric.getInterval()));
        query.add("ORDER BY time DESC");
        if (listMeasurementsParameters.getLimit() != null) {
            query.add(String.format("LIMIT %s", listMeasurementsParameters.getLimit()));
        }
        return String.join(" ", query);
    }

    private String buildListQuery(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        List<String> query = new ArrayList<>();
        query.add(String.format("SELECT %s FROM \"%s\"", Influx.FIELD_VALUE, Influx.MEASUREMENT));
        query.add(String.format("WHERE %s", buildQueryWhere(resourceName, metricName, listMeasurementsParameters)));
        query.add("ORDER BY time DESC");
        if (listMeasurementsParameters.getLimit() != null) {
            query.add(String.format("LIMIT %s", listMeasurementsParameters.getLimit()));
        }
        return String.join(" ", query);
    }

    private String buildQueryWhere(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        List<QueryWhereElement> queryWhereElements = new ArrayList<>();
        queryWhereElements.add(new QueryWhereElement(Influx.FIELD_METRIC_NAME, metricName, InfluxOperator.EQ));
        queryWhereElements.add(new QueryWhereElement(Influx.FIELD_RESOURCE_NAME, resourceName, InfluxOperator.EQ));
        if (listMeasurementsParameters.getFrom() != null) {
            queryWhereElements.add(new QueryWhereElement(Influx.FIELD_TIME, dateParsingService.fromTimestamp(listMeasurementsParameters.getFrom()), InfluxOperator.GT));
        }
        if (listMeasurementsParameters.getTo() != null) {
            queryWhereElements.add(new QueryWhereElement(Influx.FIELD_TIME, dateParsingService.fromTimestamp(listMeasurementsParameters.getTo()), InfluxOperator.LT));
        }
        return queryWhereElements.stream()
                .map(entry -> String.format("%s %s '%s'", entry.getField(), entry.getOperator().getValue(), entry.getValue()))
                .collect(Collectors.joining(" AND "));
    }

    private Stream<Measurement> toMeasurements(QueryResult.Result queryResult) {
        return Optional.ofNullable(queryResult.getSeries())
                .map(Collection::stream)
                .orElse(Stream.empty())
                .flatMap(this::seriesToMeasurement);
    }

    private Stream<Measurement> seriesToMeasurement(QueryResult.Series series) {
        return series.getValues().stream()
                .map(this::valueToMeasurement);
    }

    private Measurement valueToMeasurement(List<Object> values) {
        Measurement measurement = new Measurement();
        measurement.setTime(dateParsingService.toTimestamp((String) values.get(0)));
        measurement.setValue((double) values.get(1));
        return measurement;
    }

    @Override
    public void add(String resourceName, String metricName, Measurement measurement) {
        LOGGER.log(Level.FINE, "adding: resourceName={0} metricName={1} {2}", new Object[]{resourceName, metricName, measurement});
        Point point = Point.measurement(Influx.MEASUREMENT)
                .time(measurement.getTime(), TimeUnit.SECONDS)
                .tag(Influx.FIELD_RESOURCE_NAME, resourceName)
                .tag(Influx.FIELD_METRIC_NAME, metricName)
                .addField(Influx.FIELD_VALUE, measurement.getValue())
                .build();
        influxService.write(point);
    }

}
