package com.group.internet.banking.model;

import java.util.Date;

public class TransactionRequest {
    private String fromAccount;
    private Date fromTime;
    private Date toTime;
    private Boolean withReversal = true;

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Boolean getWithReversal() { return withReversal; }

    public void setWithReversal(Boolean withReversal) { this.withReversal = withReversal; }

    @Override
    public String toString() {
        return "Transaction [fromAccount=" + fromAccount + ", fromTime=" + fromTime + ", toTime=" + toTime
                + ", withReversal=" + withReversal
                + "]";
    }
}
