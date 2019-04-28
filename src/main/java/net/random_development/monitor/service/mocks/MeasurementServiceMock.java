package net.random_development.monitor.service.mocks;

import net.random_development.monitor.dto.ListMeasurementsParameters;
import net.random_development.monitor.dto.Measurement;
import net.random_development.monitor.service.MeasurementService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementServiceMock implements MeasurementService {

    @Override
    public List<Measurement> list(String resourceName, String metricName, ListMeasurementsParameters listMeasurementsParameters) {
        List<Measurement> measurements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            measurements.add(mockMeasurement(i));
        }
        return measurements;
    }

    private Measurement mockMeasurement(int i) {
        Measurement measurement = new Measurement();
        measurement.setTime(i * 100000);
        measurement.setValue(i);
        return measurement;
    }

    @Override
    public void add(String resourceName, String metricName, Measurement measurement) {

    }

}
