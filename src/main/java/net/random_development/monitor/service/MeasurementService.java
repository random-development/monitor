package net.random_development.monitor.service;

import net.random_development.monitor.dto.Measurement;
import net.random_development.monitor.dto.ListMeasurementsParameters;

import java.util.List;

public interface MeasurementService {
    List<Measurement> list(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters);

    void add(String resourceName, String metricName, Measurement measurement);
}
