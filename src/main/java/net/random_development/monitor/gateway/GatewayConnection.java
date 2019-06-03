package net.random_development.monitor.gateway;

import com.google.gson.JsonObject;
import net.random_development.monitor.configuration.DefaultConfiguration;
import net.random_development.monitor.data.InfluxServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GatewayConnection {

    @Value("${api.gateway.monitor.name}")
    private String monitorName;

    @Value("${api.gateway.url.monitor}")
    private String gatewayUrlMonitor;

    @Value("${api.gateway.url.registration}")
    private String gatewayUrlRegistration;

    private static final Logger LOGGER = Logger.getLogger(GatewayConnection.class.getName());

    public void registerToGateway() {
        LOGGER.log(Level.FINE, "name: {0}", monitorName);
        LOGGER.log(Level.FINE, "url: {0}", gatewayUrlMonitor);

        JsonObject json = new JsonObject();
        json.addProperty("name", monitorName);
        json.addProperty("url", gatewayUrlMonitor);

        LOGGER.log(Level.FINE, "json: {0}", json.toString());

        StringEntity entity = new StringEntity(json.toString(),
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(gatewayUrlRegistration);
        request.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(request);
            LOGGER.log(Level.FINE, "gateway registration response: {0}", response);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "gateway registration error: {0}", ex);
        }
    }

}
