package org.komarichyn.client;

import org.apache.xmlrpc.XmlRpcException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.komarichyn.server.Server;
import org.komarichyn.server.ServerModule;
import org.komarichyn.server.jpa.IPlayerDAO;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@GuiceModules({ClientModule.class, ServerModule.class})
@RunWith(GuiceTestRunner.class)
public class ClientTest {

    @Inject
    Client client;

    @Inject
    Server server;

    @Inject
    private IPlayerDAO playerDAO;

    @Inject
    private EntityManager em;

    private EntityManager getEntityManager(){
        return em;
    }

    @Before
    public void setUp() {
        try {
            server.start();
            client.init();
        } catch (IOException | SchedulerException | XmlRpcException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown()  {
        try {
            server.stop();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSend() {
        Request request = new Request ("Harry Potter", 57l, 135f);                  //add user balance to DB wallet
        Response response = client.send(request);

        Float currentBalance = response.getBalanceAfterChange();
        Float balanceChange = 30f;
        Float expectedBalance = currentBalance+balanceChange;

        request = new Request ("Harry Potter", 57l, balanceChange);                 //update user balance
        response = client.send(request);

        assertEquals(new Long(57l), response.getTransactionId());
        assertEquals(balanceChange, response.getBalanceChange());
        assertEquals(expectedBalance, response.getBalanceAfterChange());

        getEntityManager()
                .remove(playerDAO.findLatestSpecificUser("Harry Potter"));          //delete added balances after test
        getEntityManager()
                .remove(playerDAO.findLatestSpecificUser("Harry Potter"));
    }

}
