package net.random_development.monitor.service;

import net.random_development.monitor.dto.Measurement;
import net.random_development.monitor.entity.ComplexMetric;
import net.random_development.monitor.gateway.GatewayConnection;
import net.random_development.monitor.repository.ComplexMetricRepository;
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
    public static final int NUM_COMPLEX_METRICS = 5;
    public static final int NUM_MEASUREMENTS = 100;

    private final MeasurementService measurementService;
    private final ComplexMetricRepository complexMetricRepository;

    @Autowired
    public MockDataService(MeasurementService measurementService,
                           ComplexMetricRepository complexMetricRepository) {
        this.measurementService = measurementService;
        this.complexMetricRepository = complexMetricRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertMockData() {
        GatewayConnection.getInstance().registerToGateway();
        for (int resourceI = 0; resourceI < NUM_RESOURCES; resourceI++) {
            String resourceName = getResourceName(resourceI);
            insertMetrics(resourceName);
            insertComplexMetrics(resourceName);
        }
    }


    private void insertComplexMetrics(String resourceName) {
        for (int metricI = 0; metricI < NUM_COMPLEX_METRICS; metricI++) {
            String metricName = getComplexMetricName(metricI);

            ComplexMetric complexMetric = new ComplexMetric();
            complexMetric.setName(metricName);
            complexMetric.setSourceResource(resourceName);
            complexMetric.setSourceMetric(getMetricName(0));
            complexMetric.setInterval(5 + metricI);
            complexMetric.setPeriod(2 * (5 + metricI));

            complexMetricRepository.save(complexMetric);
        }
    }

    private void insertMetrics(String resourceName) {
        int timestamp = (int) (new DateTime().getMillis() / 1000);
        Random random = new Random();

        for (int metricI = 0; metricI < NUM_METRICS; metricI++) {
            String metricName = getMetricName(metricI);
            for (int dataPointI = 0; dataPointI < NUM_MEASUREMENTS; dataPointI++) {
                Measurement measurement = new Measurement();
                measurement.setTime(timestamp - dataPointI);
                measurement.setValue(random.nextDouble());
                measurementService.add(resourceName, metricName, measurement);
            }

        }
    }

    private String getMetricName(int i) {
        return String.format("metricName%d", i);
    }

    private String getResourceName(int i) {
        return String.format("resourceName%d", i);
    }

    private String getComplexMetricName(int i) {
        return String.format("complexMetricName%d", i);
    }

}
