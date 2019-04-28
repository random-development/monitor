package net.random_development.monitor.websocket;

import com.google.gson.Gson;
import net.random_development.monitor.dto.Measurement;
import net.random_development.monitor.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SensorWebsocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = Logger.getLogger(SensorWebsocketHandler.class.getName());

    private final MeasurementService measurementService;

    @Autowired
    public SensorWebsocketHandler(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String resourceName = getResourceName(session.getUri().toString());
        String metricName = getMetricName(session.getUri().toString());
        Measurement measurement = getMeasurement(message);
        LOGGER.log(Level.FINE, "adding: {0}-{1}-{2}", new Object[]{resourceName, metricName, measurement});
        measurementService.add(resourceName, metricName, measurement);
    }

    private Measurement getMeasurement(TextMessage message) {
        Gson gson = new Gson();
        return gson.fromJson(message.getPayload(), Measurement.class);
    }

    private String getMetricName(String uri) {
        String[] split = splitUrl(uri);
        return split[split.length - 1];
    }


    private String getResourceName(String uri) {
        String[] split = splitUrl(uri);
        return split[split.length - 3];
    }

    private String[] splitUrl(String uri) {
        return uri.split("/");
    }

}
