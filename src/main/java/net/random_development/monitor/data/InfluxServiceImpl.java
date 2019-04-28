package net.random_development.monitor.data;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InfluxServiceImpl implements InfluxService {

    private static final Logger LOGGER = Logger.getLogger(InfluxServiceImpl.class.getName());

    private final InfluxConfig influxConfig;

    private InfluxDB influxDB;

    @Autowired
    public InfluxServiceImpl(InfluxConfig influxConfig) {
        this.influxConfig = influxConfig;
        init();
    }

    private void init() {
        influxDB = InfluxDBFactory.connect(influxConfig.getUrl(), influxConfig.getUsername(), influxConfig.getPassword());

        influxDB.query(new Query("CREATE DATABASE " + influxConfig.getDatabaseName()));
        influxDB.setDatabase(influxConfig.getDatabaseName());

        String rpName = "aRetentionPolicy";
        influxDB.query(new Query("CREATE RETENTION POLICY " + rpName + " ON " + influxConfig.getDatabaseName() + " DURATION 30h REPLICATION 2 SHARD DURATION 30m DEFAULT"));
        influxDB.setRetentionPolicy(rpName);
    }


    @Override
    public void write(Point point) {
        influxDB.write(point);
    }

    @Override
    public List<QueryResult.Result> query(String query) {
        LOGGER.log(Level.FINE, "executing query: {0}", query);
        QueryResult queryResult = influxDB.query(new Query(query, influxConfig.getDatabaseName()));
        if (queryResult.hasError()) {
            throw new RuntimeException(queryResult.getError());
        }
        return queryResult.getResults();
    }
}
