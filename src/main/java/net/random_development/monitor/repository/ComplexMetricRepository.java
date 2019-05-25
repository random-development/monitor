package net.random_development.monitor.repository;

import net.random_development.monitor.entity.ComplexMetric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexMetricRepository extends CrudRepository<ComplexMetric, String> {
}
