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

//    @Value("${api.gateway.monitor.name}")
//    private String monitorName;
//
//    @Value("${api.gateway.url}")
//    private String gatewayUrl;
//
//    @Autowired
//    public GatewayConnection(){}

    private static GatewayConnection INSTANCE = null;
    private static final Logger LOGGER = Logger.getLogger(GatewayConnection.class.getName());

    private  GatewayConnection() {}

    public static GatewayConnection getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GatewayConnection();
        return INSTANCE;
    }

    public void registerToGateway() {
        String monitorName = "monitor2";
        String gatewayUrlMonitor = "http://hibron.usermd.net:5000/mock/monitors/monitor2/";
        String gatewayUrl = "http://hibron.usermd.net:5000/monitor/manage";
        LOGGER.log(Level.FINE, "name: {0}", monitorName);
        LOGGER.log(Level.FINE, "url: {0}", gatewayUrlMonitor);

        JsonObject json = new JsonObject();
        json.addProperty("name", monitorName);
        json.addProperty("url", gatewayUrlMonitor);

        StringEntity entity = new StringEntity(json.toString(),
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(gatewayUrl);
        request.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(request);
            LOGGER.log(Level.FINE, "gateway registration response: {0}", response);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "gateway registration error: {0}", ex);
        }
    }

}
