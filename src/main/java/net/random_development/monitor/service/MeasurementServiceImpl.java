package net.random_development.monitor.service;

import net.random_development.monitor.data.InfluxService;
import net.random_development.monitor.dto.ListMeasurementsParameters;
import net.random_development.monitor.dto.Measurement;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private static final String FIELD_VALUE = "value";
    private static final String FIELD_RESOURCE_NAME = "resource";

    private final InfluxService influxService;

    @Autowired
    public MeasurementServiceImpl(InfluxService influxService) {
        this.influxService = influxService;
    }

    @Override
    public List<Measurement> list(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s'", FIELD_VALUE, metricName, FIELD_RESOURCE_NAME, resourceName);
        return influxService.query(query).stream()
                .flatMap(this::toMeasurements)
                .collect(Collectors.toList());
    }

    private Stream<Measurement> toMeasurements(QueryResult.Result queryResult) {
        return Optional.ofNullable(queryResult.getSeries())
                .map(Collection::stream)
                .orElseThrow(() -> new RuntimeException("error"))
                .flatMap(this::seriesToMeasurement);
    }

    private Stream<Measurement> seriesToMeasurement(QueryResult.Series series) {
        return series.getValues().stream()
                .map(this::valueToMeasurement);
    }

    private Measurement valueToMeasurement(List<Object> values) {
        Measurement measurement = new Measurement();
        measurement.setTime(toTime((String) values.get(0)));
        measurement.setValue((double) values.get(1));
        return measurement;
    }

    private int toTime(String s) {
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
        return (int) (parser.parseDateTime(s).getMillis() / 1000);
    }

    @Override
    public void add(String resourceName, String metricName, Measurement measurement) {
        Point point = Point.measurement(metricName)
                .time(measurement.getTime(), TimeUnit.SECONDS)
                .tag(FIELD_RESOURCE_NAME, resourceName)
                .addField(FIELD_VALUE, measurement.getValue())
                .build();
        influxService.write(point);
    }

}
