package net.random_development.monitor.service;

import net.random_development.monitor.gateway.GatewayConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GatewayRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(GatewayRegistrationService.class.getName());

    private final GatewayConnection gatewayConnection;

    @Autowired
    public GatewayRegistrationService(GatewayConnection gatewayConnection) {
        this.gatewayConnection = gatewayConnection;
    }

    @Scheduled(fixedDelay = Delay.MINUTE)
    public void register() {
        LOGGER.info("registering");
        try {
            gatewayConnection.registerToGateway();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
