package org.komarichyn.server.handlers;

import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.komarichyn.server.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WalletHandler {
    private static final Logger log = LoggerFactory.getLogger(WalletHandler.class);

    @Inject
    private Service service;

    public Response updateBalance(Request request) {
        log.debug("update balance with request: {}", request);
        return service.updateWallet(request);
    }
}
