package org.komarichyn.server.jpa.dao;

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
import org.komarichyn.common.guice.jpa.JPAPlayerModule;
import org.komarichyn.junit.GuiceModules;
import org.komarichyn.junit.GuiceTestRunner;
import org.komarichyn.server.jpa.IPlayerDAO;
import org.komarichyn.server.jpa.model.Player;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@GuiceModules(JPAPlayerModule.class)
@RunWith(GuiceTestRunner.class)
public class ClientDAOTest {

    @Inject
    private EntityManager em;
    @Inject
    private IPlayerDAO playerDAO;

    private IDatabaseConnection connection;
    private IDataSet dataset;

    private InputStream getResource(String str) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
    }

    @Transactional
    public void init() {

        java.sql.Connection conn = ((EntityManagerImpl) em).getServerSession().getAccessor().getConnection();
        try {
            //Initializes DBUnit
            connection = new DatabaseConnection(conn);
            dataset = new FlatXmlDataSetBuilder().build(getResource("dbunit/Client.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        } catch (SQLException | DatabaseUnitException e) {
            e.printStackTrace();
        }
        // Cleans the database with DbUnit
    }

    public void destroy() {
        try {
            DatabaseOperation.DELETE_ALL.execute(connection, dataset);
        } catch (DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        init();
    }

    @After
    public void ternDown() {
        destroy();
    }

    @Test
    public void sameTest(){
        assertNotNull(playerDAO);
    }

}
