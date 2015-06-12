package org.komarichyn.server.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PLAYER", indexes = {@Index(name = "USERNAME_IDX", unique = false, columnList = "USERNAME")})
@NamedQueries({
        @NamedQuery(name = "findLatestSpecificUser", query = "SELECT p FROM Player p WHERE p.username=:userName AND p.balanceVersion = (SELECT MAX(m.balanceVersion) FROM Player m)"),
})
public class Player {
    //PLAYER(USERNAME, BALANCE_VERSION, BALANCE)

    @Column(name = "USERNAME")
    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BALANCE_VERSION")
    private Long balanceVersion;
    @Column(name = "BALANCE")
    private Float balance;

    public Player(){}
    public Player(String username){
        this.username = username;
        this.balanceVersion = 0l;
        this.balance = 0f;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBalanceVersion() {
        return balanceVersion;
    }

    public void setBalanceVersion(Long balanceVersion) {
        this.balanceVersion = balanceVersion;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public void updateBalance(Float balanceChange){
        this.balance += balanceChange;
    }
}
