package org.komarichyn.server;

import com.google.inject.Singleton;
import org.komarichyn.common.ErrorCode;
import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.komarichyn.server.jpa.IPlayerDAO;
import org.komarichyn.server.jpa.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class Service {

    private static final Logger log = LoggerFactory.getLogger(Service.class);


    AtomicReference<HashMap<Long, Request>> atomicReference = new AtomicReference<>(new HashMap<Long, Request>());

    @Inject
    private IPlayerDAO playerDAO;

    public Response updateWallet(Request request) {
        log.debug("update wallet: {}", request);
        //collect request
        atomicReference.get().put(request.getTransactionId(), request);

        Player player = playerDAO.newInstance();
        player.setUsername(request.getUserName());
        Player playerIfExists = playerDAO.findLatestSpecificUser(request.getUserName());

        if (playerIfExists==null){
            player.setBalance(request.getBalanceChange());
        }
        else{
            player.setBalance(playerIfExists.getBalance());
            player.updateBalance(request.getBalanceChange());
        }
        playerDAO.saveBalance(player);
        request.processingDone();

        log.debug("request: {} was processed", request);

        Response response = new Response(request.getTransactionId(),
                ErrorCode.OK,
                player.getBalanceVersion(),
                request.getBalanceChange(), player.getBalance());

        log.debug("response: {} was assembled");
        return response;
    }

    public void persistenceStatistics(){

        Map<Long, Request> tmpMap  = atomicReference.getAndSet(new HashMap<Long, Request>());
        double averageTime = 0;
        long minTime = 0l;
        long maxTime = 0l;
        int logSize = tmpMap.size();
        for (Request request : tmpMap.values()) {
            long duration = request.getDuration();
            averageTime += duration;
            if (minTime > duration) {
                minTime = duration;
            }
            if (maxTime < duration) {
                maxTime = duration;
            }
        }
        if (logSize != 0)
            averageTime /= logSize;

        log.info("statistics info: {}", "amount processed commands=" + logSize
                + "\t" + "average processing time=" + averageTime + "ms\t"
                + "max processing time=" + maxTime + "ms\t"
                + "min processing time=" + minTime + "ms\t");
    }
}
