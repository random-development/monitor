package net.random_development.monitor.service;

import net.random_development.monitor.gateway.GatewayConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GatewayRegistrationServiceTest {

    @Mock
    private GatewayConnection gatewayConnection;

    private GatewayRegistrationService gatewayRegistrationService;

    @Before
    public void setUp() {
        initMocks(this);

        gatewayRegistrationService = new GatewayRegistrationService(gatewayConnection);
    }

    @Test
    public void shouldCallRegister() {
        gatewayRegistrationService.register();
        verify(gatewayConnection, times(1)).registerToGateway();
    }

}
