package org.komarichyn.server.jpa.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.komarichyn.server.jpa.IPlayerDAO;
import org.komarichyn.server.jpa.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class PlayerDAO implements IPlayerDAO {
    private static final Logger log = LoggerFactory.getLogger(PlayerDAO.class);

    @Inject
    private Provider<EntityManager> emProvider;

    public EntityManager getEntityManager() {
        return emProvider.get();
    }

    @Override
    @Transactional
    public Player saveBalance(final Player player) {
        log.debug("save new entity: {}", player);
        EntityManager em = getEntityManager();
        if (!em.isJoinedToTransaction()) em.joinTransaction();
        em.persist(player);
        em.flush();
        return player;
    }

    @Override
    @Transactional
    public Float findLatestBalance(String userName) {
        List<Player> latestBalance = getEntityManager().createNamedQuery("findLatestSpecificUser", Player.class)
                .setParameter("userName", userName).getResultList();
        return latestBalance.isEmpty() ? null : latestBalance.get(0).getBalance();
    }

    @Override
    @Transactional
    public void deletePlayerBalanceVersion(final Long id) {
        log.debug("remove entity by id: {}", id);
        Player instance = getEntityManager().find(Player.class, id);
        if (instance == null) {
            log.error("can't find entity by id : {}", id);
            return;
        }
        EntityManager em = getEntityManager();
        em.remove(instance);
        em.flush();
    }

    @Override
    @Transactional
    public Player findPlayerByBalanceVersion(final Long id) {
        if (id == null) {
            log.error("entity id can't be null");
            return null;
        }
        return getEntityManager().find(Player.class, id);
    }

    @Override
    @Transactional
    public Player update(final Player player) {
        log.debug("update player: {}", player);
        EntityManager em = getEntityManager();
        Player mergedPlayer = em.merge(player);
        em.flush();
        return mergedPlayer;
    }

    @Override
    public Player newInstance() {
        return new Player();
    }

    @Override
    @Transactional
    public Player findLatestSpecificUser(String name) {
        List<Player> list = getEntityManager().createNamedQuery("findLatestSpecificUser", Player.class)
                .setParameter("userName", name).getResultList();
        return (list.isEmpty()) ? null : list.get(0);
    }
}
