package org.komarichyn.server;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komarichyn.common.Request;
import org.komarichyn.common.Response;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.komarichyn.server.jpa.IPlayerDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static junit.framework.Assert.assertEquals;

@GuiceModules({ServerModule.class})
@RunWith(GuiceTestRunner.class)
public class ServiceTest {

    @Inject
    private IPlayerDAO playerDAO;

    @Inject
    private Provider<EntityManager> em;

    private EntityManager getEntityManager(){
        return em.get();
    }

    @Inject
    private Service service;

    @Test
    public void testUpdateWallet() {
        Request request = new Request ("Cool Man", 50l, 135f);                  //add user balance to DB
        Response response = service.updateWallet(request);

        Float currentBalance = response.getBalanceAfterChange();
        Float balanceChange = 30f;
        Float expectedBalance = currentBalance+balanceChange;

        request = new Request ("Cool Man", 50l, balanceChange);                 //update user balance
        response = service.updateWallet(request);

        assertEquals(new Long(50l), response.getTransactionId());
        assertEquals(balanceChange, response.getBalanceChange());
        assertEquals(expectedBalance, response.getBalanceAfterChange());

        getEntityManager().
                remove(playerDAO.findLatestSpecificUser("Cool Man"));           //delete added balances after test
        getEntityManager().
                remove(playerDAO.findLatestSpecificUser("Cool Man"));
    }
}