package net.random_development.monitor.configuration;

import net.random_development.monitor.data.InfluxConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {

    @Value("${influx.url}")
    private String influxUrl;

    @Value("${influx.user}")
    private String influxUser;

    @Value("${influx.password}")
    private String influxPassword;

    @Value("${influx.database-name}")
    private String influxDatabaseName;

    @Bean
    public InfluxConfig influxConfig() {
        return new InfluxConfig(influxUrl, influxUser, influxPassword, influxDatabaseName);
    }

}
