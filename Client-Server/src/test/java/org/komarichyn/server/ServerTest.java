package org.komarichyn.server;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komarichyn.client.ClientModule;
import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@GuiceModules({ServerModule.class, ClientModule.class})
@RunWith(GuiceTestRunner.class)
public class ServerTest {

    @Inject
    Server server;

    @Before
    public void setUp()  {
        try {
            server.start();
        } catch (IOException | XmlRpcException | SchedulerException e) {
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
    public void testStart() {
        assertNotNull(server);
    }

    @Test
    public void testUpdateBalance() throws MalformedURLException, XmlRpcException {
        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:5555/xmlrpc"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        XmlRpcClient client = new XmlRpcClient();

        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));

        client.setConfig(config);

        Request request = new Request("request name", 7777l, 0f);
        Response result = (Response) client.execute("wallet.updateBalance", new Object[]{request});
        assertNotNull(result);
        assertTrue(7777l == result.getTransactionId());
        assertTrue(0f == result.getBalanceChange());
    }

}
