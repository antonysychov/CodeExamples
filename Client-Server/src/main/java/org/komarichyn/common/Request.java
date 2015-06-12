package org.komarichyn.common;

import java.io.Serializable;
import java.util.Date;


public class Request implements Serializable {

    private String userName;
    private Long transactionId;
    private Float balanceChange;
    private Date when = new Date();
    private Long duration;
    private Date end;

    public Request() {
        when = new Date();
    }

    public Request(String userName, Long transactionId, Float balanceChange) {
        this.userName = userName;
        this.transactionId = transactionId;
        this.balanceChange = balanceChange;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Float getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(Float balanceChange) {
        this.balanceChange = balanceChange;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void processingDone(){
        end = new Date();
        duration = end.getTime() - when.getTime();
    }

    @Override
    public String toString() {
        return "Request{" + "userName='" + userName + '\'' + ", transactionId=" + transactionId + ", balanceChange=" + balanceChange + ", when=" + when + ", duration=" + duration + ", end=" + end + '}';
    }
}
