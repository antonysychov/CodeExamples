package org.komarichyn.client;

import com.google.inject.Provider;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.komarichyn.server.Server;
import org.komarichyn.server.ServerModule;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@GuiceModules({ClientModule.class, ServerModule.class})
@RunWith(GuiceTestRunner.class)
public class ServiceTest {

    @Inject
    private Service service;

    @Inject
    private Server server;

    @Inject
    private Provider<EntityManager> em;

    private EntityManager getEntityManager(){
        return em.get();
    }

    @Before
    public  void startServer(){
        try {
            server.start();
            service.initClient();
            service.playLogic();
        } catch (IOException | SchedulerException | XmlRpcException e) {
            e.printStackTrace();
        }
    }

    @After
    public  void stopServer(){
        try {
            server.stop();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientService(){
        assertNotNull(service);
    }

    @Test
    public void testGenerateRandomBalance() {
        float random = service.generateRandomBalance();
        assertTrue(random >= -50f && random <= 50f);
    }

}
