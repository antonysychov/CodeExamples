package org.komarichyn.server.jpa;

import org.komarichyn.server.jpa.model.Player;


public interface IPlayerDAO {
    /**
     * persist balance
     * @param player player with balance
     * @return saved player
     */
    Player saveBalance(Player player);

    Float findLatestBalance(String userName);

    /**
     * remove balance for player
     * @param id balance id
     */
    void deletePlayerBalanceVersion(Long id);

    /**
     * find player's balance by balance version
     * @param id balance version
     * @return player's balance
     */
    Player findPlayerByBalanceVersion(Long id);

    /**
     * update existing record
     * @param player existed player with balance
     * @return balance for player
     */
    Player update(Player player);                    //изменено

    Player newInstance();

    /**
     * find specific player with max balance version
     * @param name user name
     * @return balance
     */
    Player findLatestSpecificUser (String name);
}
