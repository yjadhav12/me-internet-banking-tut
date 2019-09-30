package com.group.internet.banking.model;

import org.javamoney.moneta.Money;

import java.util.Date;

public class Transaction {
    private String txnId;
    private String fromAccount;
    private String toAccount;
    private Money amount;
    private Date txnTime;
    private String txnType;
    private String relatedTxn;

    public String getRelatedTxn() {
        return relatedTxn;
    }

    public void setRelatedTxn(String relatedTxn) {
        this.relatedTxn = relatedTxn;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Date getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Date txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    @Override
    public String toString() {
        return "Transaction [txnId=" + txnId + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount
                + ", txnTime=" + txnTime+ ", txnType=" + txnType
                + "]";
    }
}
