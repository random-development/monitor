package net.random_development.monitor.service;

import net.random_development.monitor.dto.Measurement;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MockDataService {

    public static final int NUM_RESOURCES = 10;
    public static final int NUM_METRICS = 10;
    public static final int NUM_MEASUREMENTS = 100;

    private final MeasurementService measurementService;

    @Autowired
    public MockDataService(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertMockData() {
        int timestamp = (int) (new DateTime().getMillis() / 1000);
        Random random = new Random();

        for (int resourceI = 0; resourceI < NUM_RESOURCES; resourceI++) {
            String resourceName = String.format("resourceName%d", resourceI);
            for (int metricI = 0; metricI < NUM_METRICS; metricI++) {
                String metricName = String.format("metricName%d", metricI);
                for (int dataPointI = 0; dataPointI < NUM_MEASUREMENTS; dataPointI++) {
                    Measurement measurement = new Measurement();
                    measurement.setTime(timestamp - dataPointI);
                    measurement.setValue(random.nextDouble());
                    measurementService.add(resourceName, metricName, measurement);
                }

            }
        }
    }

}
