package org.komarichyn.common;

import java.io.Serializable;


public class Response implements Serializable {
    private Long transactionId;
    private int errorCode;
    private Long balanceVersion;
    private Float balanceChange;
    private Float balanceAfterChange;

    public Response() {
    }

    public Response(Long transactionId, int errorCode, Long balanceVersion, Float balanceChange, Float balanceAfterChange) {
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.balanceVersion = balanceVersion;
        this.balanceChange = balanceChange;
        this.balanceAfterChange = balanceAfterChange;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Long getBalanceVersion() {
        return balanceVersion;
    }

    public void setBalanceVersion(Long balanceVersion) {
        this.balanceVersion = balanceVersion;
    }

    public Float getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(Float balanceChange) {
        this.balanceChange = balanceChange;
    }

    public Float getBalanceAfterChange() {
        return balanceAfterChange;
    }

    public void setBalanceAfterChange(Float balanceAfterChange) {
        this.balanceAfterChange = balanceAfterChange;
    }

    @Override
    public String toString() {
        return "Response{" +
                "transactionId=" + transactionId +
                ", errorCode=" + errorCode +
                ", balanceVersion=" + balanceVersion +
                ", balanceChange=" + balanceChange +
                ", balanceAfterChange=" + balanceAfterChange +
                '}';
    }
}
