package net.random_development.monitor.configuration;

import net.random_development.monitor.repository.ComplexMetricRepository;
import net.random_development.monitor.service.MeasurementService;
import net.random_development.monitor.service.MockDataService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value = "monitor.insert.mock.data",
        havingValue = "true",
        matchIfMissing = true)
public class MockDataConfiguration {

    @Bean
    MockDataService mockDataService(MeasurementService measurementService,
                                    ComplexMetricRepository complexMetricRepository) {
        return new MockDataService(measurementService, complexMetricRepository);
    }
}
