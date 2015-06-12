package org.komarichyn.client;

import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.Random;


public class Service {

    private static final Logger log = LoggerFactory.getLogger(Service.class);
    private Random random = new Random();
    private static final float maxRangeValue = 50f;
    private static final float minRangeValue = -50f;

    @Inject
    private Client client;

    public void updateWallet() {
        log.debug("update wallet");
        Request request = new Request("Test user", System.currentTimeMillis(), generateRandomBalance());
        log.debug("request : {} was build", request);
        Response result = client.send(request);
        log.info("received response: {} for request: {}", result, request);
    }

    public void initClient() {
        try {
            client.init();
        } catch (MalformedURLException e) {
            log.error(e.getLocalizedMessage()
            );
        }
    }

    public float generateRandomBalance() {
        float balance = (maxRangeValue - minRangeValue) * random.nextFloat() + minRangeValue;
        log.debug("generated balance:{}", balance);
        return balance;
    }

    public void playLogic() {
        updateWallet();
    }

}
