package net.random_development.monitor.data;

import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;

import java.util.List;

public interface InfluxService {

    void write(Point point);

    List<QueryResult.Result> query(String query);
}
