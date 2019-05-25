package net.random_development.monitor.service;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.MetricType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(HierarchicalContextRunner.class)
public class MetricServiceImplTest {

    @Mock
    NormalMetricService normalMetricService;

    @Mock
    ComplexMetricService complexMetricService;

    private MetricService service;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        service = new MetricServiceImpl(complexMetricService, normalMetricService);
    }

    public class HasMetricAndComplexMetricWithIdenticalNames {

        private final String METRIC_NAME = "metricName";
        private final String RESOURCE_NAME = "resourceName";

        @Before
        public void returnComplexMetrics() {
            List<Metric> complexMetrics = new ArrayList<>();
            {
                Metric complexMetric = new Metric();
                complexMetric.setName(METRIC_NAME);
                complexMetric.setType(MetricType.COMPLEX);

                complexMetrics.add(complexMetric);
            }

            when(complexMetricService.list(eq(RESOURCE_NAME)))
                    .thenReturn(complexMetrics);
        }

        @Before
        public void returnNormalMetrics() {
            List<Metric> normalMetrics = new ArrayList<>();
            {
                Metric normalMetric = new Metric();
                normalMetric.setName(METRIC_NAME);
                normalMetric.setType(MetricType.NORMAL);

                normalMetrics.add(normalMetric);
            }

            when(normalMetricService.list(eq(RESOURCE_NAME)))
                    .thenReturn(normalMetrics);
        }

        @Test
        public void listShouldReturnOnlyTheComplexMetric() {
            List<Metric> list = service.list(RESOURCE_NAME);

            Assert.assertEquals(1, list.size());
            Assert.assertEquals(METRIC_NAME, list.get(0).getName());
            Assert.assertEquals(MetricType.COMPLEX, list.get(0).getType());
        }

    }

}
