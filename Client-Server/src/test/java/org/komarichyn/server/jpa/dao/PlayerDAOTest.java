package org.komarichyn.server.jpa.dao;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.komarichyn.server.ServerModule;
import org.komarichyn.server.jpa.model.Player;

import javax.persistence.EntityManager;

import java.sql.SQLException;

import static org.junit.Assert.*;


@GuiceModules({ServerModule.class})
@RunWith(GuiceTestRunner.class)
public class PlayerDAOTest {

    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private EntityManager em;

    @Before
    public void setUp() {
        dbClean();
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
    }


    @Test
    public void testGetEntityManager() {
        assertNotNull(playerDAO.getEntityManager());
    }

    @Test
    @Transactional
    public void testFindLatestSpecificUser() {
        Player firstPlayer = new Player("Donald Duck");                             //add first user balance to DB
        em.persist(firstPlayer);
        Player lastPlayer = new Player("Donald Duck");                              //add new user balance to DB
        lastPlayer.setBalance(30f);
        em.persist(lastPlayer);
        em.flush();
        Player actualLatestPlayer =                                                 //determine the latest user balance
                playerDAO.findLatestSpecificUser("Donald Duck");

        assertEquals(lastPlayer, actualLatestPlayer);
        assertNull(playerDAO.findLatestSpecificUser("nonExistentUser"));            //check test method for null return
        em.remove(firstPlayer);                                                     //delete added balances after test
        em.remove(lastPlayer);
        em.flush();
    }

    @Test
    @Transactional
    public void testSaveBalance() {
        Player expectedPlayer = new Player("Bat Man");
        Player actualPlayer = playerDAO.saveBalance(expectedPlayer);

        assertEquals(actualPlayer, expectedPlayer);
        em.remove(actualPlayer);                                                    //delete added balance after test
        em.flush();
    }

    @Test
    @Transactional
    public void testFindLatestBalance() {
        Player firstPlayer = new Player("Darkwing Duck");                           //add first user balance to DB
        firstPlayer.setBalance(10000f);
        em.persist(firstPlayer);
        Player lastPlayer = new Player("Darkwing Duck");                            //add second user balance to DB
        lastPlayer.setBalance(30000f);
        em.persist(lastPlayer);
        em.flush();
        Float expected = 30000f;
        Float actual = playerDAO.findLatestBalance("Darkwing Duck");                //determine the latest user balance

        assertEquals(expected, actual);
        assertNull(playerDAO.findLatestBalance("Nonexistent Player"));              //check for null return
        assertNull(null);

        em.remove(firstPlayer);                                                     //delete added balances after test
        em.remove(lastPlayer);
        em.flush();
    }

    @Test
    @Transactional
    public void testDeletePlayerBalanceVersion() {
        Player player = new Player("Peter Pen");                                    //create new user balance
        em.persist(player);
        em.flush();
        Long playerId = player.getBalanceVersion();
        Long nonExistentPlayerId = playerId+1;
        playerDAO.deletePlayerBalanceVersion(nonExistentPlayerId);                  //try to delete balance by nonexistent id
        assertNotNull(em.find(Player.class, playerId));                             //check if balance

        playerDAO.deletePlayerBalanceVersion(playerId);                             //delete added balance version from DB
        assertNull(em.find(Player.class, playerId));                                //check if balance version vas deleted
    }

    @Test
    @Transactional
    public void testFindPlayerByBalanceVersion() {
        Player player = new Player("Inspector Gadget");                             //create new user balance
        em.persist(player);
        em.flush();
        Long playerId = player.getBalanceVersion();
        Long nonExistentId = playerId+10;
        Player foundPlayer =                                                        //find user by obtained id
                playerDAO.findPlayerByBalanceVersion(playerId);
        Player nonExistentPlayer =
                playerDAO.findPlayerByBalanceVersion(nonExistentId);

        assertNull(nonExistentPlayer);                                              //check method for null return
        assertNull(playerDAO.findPlayerByBalanceVersion(null));
        assertEquals(foundPlayer, player);

        em.remove(player);                                                          //delete added balance after test
        em.flush();
    }

    @Test
    @Transactional
    public void testUpdate() {
        Player player = new Player("Homer Simpson");                                //create user balance to be updated
        em.persist(player);
        em.flush();
        player.setBalance(-5f);

        Float updatedUserBalance =
                playerDAO.update(player).getBalance();                              //update by test method and get updated balance

        assertEquals(new Float(-5f), updatedUserBalance);
        em.remove(player);                                                          //delete added balances after test
        em.flush();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(playerDAO.newInstance() );
    }

    private void dbClean() {
        java.sql.Connection conn = ((EntityManagerImpl) em).getServerSession().getAccessor().getConnection();
        try {
            IDatabaseConnection connection = new DatabaseConnection(conn);
            IDataSet dataset = new FlatXmlDataSetBuilder()
                    .build(Thread.currentThread().getContextClassLoader().getResourceAsStream("dbunit/Client.xml"));
            try {
                DatabaseOperation.DELETE_ALL.execute(connection, dataset);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }
    }
}