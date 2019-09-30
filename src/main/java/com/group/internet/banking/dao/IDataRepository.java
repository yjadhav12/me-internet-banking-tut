package com.group.internet.banking.dao;

import com.group.internet.banking.model.Transaction;
import com.group.internet.banking.model.TransactionRequest;

import java.util.List;

public interface IDataRepository {

    List<Transaction> getTransactions(TransactionRequest request);

    List<Transaction> getReversalTransactions(TransactionRequest request);

    Transaction getTransactionsById(String txnId);

}
