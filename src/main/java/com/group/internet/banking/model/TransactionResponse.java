package com.group.internet.banking.model;

import org.javamoney.moneta.Money;

public class TransactionResponse {

    private Money balance;
    private Integer numberOfTxns;

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Integer getNumberOfTxns() {
        return numberOfTxns;
    }

    public void setNumberOfTxns(Integer numberOfTxns) {
        this.numberOfTxns = numberOfTxns;
    }

    @Override
    public String toString() {
        return "Transaction [balance=" + balance + ", numberOfTxns=" + numberOfTxns
                + "]";
    }
}
