package net.random_development.monitor.configuration;

import net.random_development.monitor.data.InfluxConfig;
import net.random_development.monitor.websocket.SensorWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class DefaultConfiguration implements WebSocketConfigurer {

    @Value("${influx.url}")
    private String influxUrl;

    @Value("${influx.user}")
    private String influxUser;

    @Value("${influx.password}")
    private String influxPassword;

    @Autowired
    private SensorWebsocketHandler sensorWebsocketHandler;

    @Bean
    public InfluxConfig influxConfig() {
        return new InfluxConfig(influxUrl, influxUser, influxPassword);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sensorWebsocketHandler, "/upload/**");
    }
}
