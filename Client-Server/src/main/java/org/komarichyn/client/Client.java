package org.komarichyn.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;


public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private XmlRpcClient client;

    public Client(){
        client = new XmlRpcClient();
    }

    public void init() throws MalformedURLException {
        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:5555/xmlrpc"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));

        client.setConfig(config);

    }

    public Response send(Request request) {
        Response response = null;
        try {
            response = (Response)client.execute( "wallet.updateBalance", new Object[]{request});
        } catch (XmlRpcException e) {
            log.error("update balance failed", e);
        }
        return response;
    }
}
