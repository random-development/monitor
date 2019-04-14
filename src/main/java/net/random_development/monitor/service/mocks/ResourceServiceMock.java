package net.random_development.monitor.service.mocks;

import net.random_development.monitor.dto.Resource;
import net.random_development.monitor.service.MetricService;
import net.random_development.monitor.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceMock implements ResourceService {

    private final MetricService metricService;

    @Autowired
    public ResourceServiceMock(MetricService metricService) {
        this.metricService = metricService;
    }

    @Override
    public List<Resource> list() {
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            resources.add(mockResource(i));
        }
        return resources;
    }

    @Override
    public Resource get(String resourceName) {
        return mockResource(0);
    }

    private Resource mockResource(int i) {
        String resourceName = String.format("resource-%s", i);
        Resource resource = new Resource();
        resource.setName(resourceName);
        resource.setMetrics(metricService.list(resourceName));
        return resource;
    }

}
