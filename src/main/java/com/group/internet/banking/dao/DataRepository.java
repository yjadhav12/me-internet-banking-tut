package com.group.internet.banking.dao;

import com.group.internet.banking.model.Transaction;
import com.group.internet.banking.model.TransactionRequest;
import com.group.internet.banking.util.AppConstants;
import com.group.internet.banking.util.CsvReader;

import java.util.List;
import java.util.stream.Collectors;

public final class DataRepository implements IDataRepository{

    private static DataRepository instance = null;
    private final List<Transaction> transactions;

    private DataRepository() throws Exception{
        this.transactions = CsvReader.readFromCSV("src/main/resources/mockData.csv");
    }

    public static DataRepository getInstance(){
        if(instance==null) {
            try {
                instance = new DataRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return instance;
    }

    public List<Transaction> getTransactions(TransactionRequest request) {
        return this.transactions.stream()
                .filter(txn -> txn.getFromAccount().equals(request.getFromAccount()) && (txn.getTxnTime().compareTo(request.getFromTime())==0 || txn.getTxnTime().after(request.getFromTime()))
                        && txn.getTxnTime().before(request.getToTime())).collect(Collectors.toList());
    }

    public List<Transaction> getReversalTransactions(TransactionRequest request) {
        return transactions.stream().
                filter(txn -> txn.getFromAccount().equals(request.getFromAccount()) &&  txn.getTxnType().equalsIgnoreCase(AppConstants.PAYMENT_TYPE.REVERSAL.toString())).collect(Collectors.toList());
    }

    public Transaction getTransactionsById(String txnId) {
        return this.transactions.stream()
                .filter(txn -> txn.getTxnId().equalsIgnoreCase(txnId)).findFirst().get();
    }


}
