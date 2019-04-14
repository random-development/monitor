package net.random_development.monitor.controller;

import net.random_development.monitor.dto.ListMeasurementsParameters;
import net.random_development.monitor.dto.Metric;
import net.random_development.monitor.dto.Resource;
import net.random_development.monitor.exception.NotFoundApiException;
import net.random_development.monitor.service.MeasurementService;
import net.random_development.monitor.service.MetricService;
import net.random_development.monitor.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final MetricService metricService;
    private final MeasurementService measurementService;

    @Autowired
    public ResourceController(ResourceService resourceService,
                              MetricService metricService,
                              MeasurementService measurementService) {
        this.resourceService = resourceService;
        this.metricService = metricService;
        this.measurementService = measurementService;
    }

    @GetMapping
    public ResponseEntity<List<Resource>> list() {
        return Optional.ofNullable(resourceService.list())
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundApiException::new);
    }

    @GetMapping("/{resourceName}")
    public ResponseEntity<Resource> getResource(@PathVariable("resourceName") String resourceName) {
        return Optional.ofNullable(resourceService.get(resourceName))
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundApiException::new);
    }

    @GetMapping("/{resourceName}/metrics")
    public ResponseEntity<List<Metric>> listMetrics(@PathVariable("resourceName") String resourceName) {
        return Optional.ofNullable(metricService.list(resourceName))
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundApiException::new);
    }

    @PostMapping("/{resourceName}/metrics")
    public ResponseEntity<Metric> postMetrics(@PathVariable("resourceName") String resourceName,
                                              @RequestBody Metric metric) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(metricService.create(metric));
    }

    @GetMapping("/{resourceName}/metrics/{metricName}")
    public ResponseEntity<Metric> getMetric(@PathVariable("resourceName") String resourceName,
                                            @PathVariable("metricName") String metricName) {
        return Optional.ofNullable(metricService.get(resourceName, metricName))
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundApiException::new);
    }

    @DeleteMapping("/{resourceName}/metrics/{metricName}")
    public ResponseEntity<Void> deleteMetric(@PathVariable("resourceName") String resourceName,
                                             @PathVariable("metricName") String metricName) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{resourceName}/metrics/{metricName}/measurements")
    public ResponseEntity<List<Measurement>> listMeasurements(@PathVariable("resourceName") String resourceName,
                                                         @PathVariable("metricName") String metricName,
                                                         ListMeasurementsParameters listMeasurementsParameters) {
        return Optional.ofNullable(measurementService.list(resourceName, metricName, listMeasurementsParameters))
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundApiException::new);
    }

}
