package net.random_development.monitor.data;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfluxServiceImpl implements InfluxService {

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

}
