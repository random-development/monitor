package net.random_development.monitor.repository;

import net.random_development.monitor.entity.ComplexMetric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplexMetricRepository extends CrudRepository<ComplexMetric, String> {

    List<ComplexMetric> findBySourceResource(String sourceResource);

    Optional<ComplexMetric> getBySourceResourceAndName(String resourceName, String metricName);

    @Transactional
    int deleteBySourceResourceAndName(String resourceName, String metricName);

}
