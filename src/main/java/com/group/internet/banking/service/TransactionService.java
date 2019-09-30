package com.group.internet.banking.service;

import com.group.internet.banking.dao.DataRepository;
import com.group.internet.banking.exception.BusinessException;
import com.group.internet.banking.model.Transaction;
import com.group.internet.banking.model.TransactionListResponse;
import com.group.internet.banking.model.TransactionRequest;
import com.group.internet.banking.model.TransactionResponse;
import com.group.internet.banking.util.AppConstants;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionService {

    private DataRepository dataRepository;
    private final static Logger logger = Logger.getLogger(TransactionService.class.getName());

    public TransactionResponse checkBalance(TransactionRequest request) throws BusinessException {

        logger.info("checkBalance - request for: "+ request.getFromAccount());

        TransactionResponse response = new TransactionResponse();
        dataRepository = DataRepository.getInstance();

        //I could have done all these below operations on single line code but for the sake of readability,
        //I prefer to split the stream/lambda operations to understand the code in better way
        List<Transaction> transactions = getCreditedTransactions(request).getTransactionList();
        List<Transaction> reverseTransactions = getReversalTransactions(request).getTransactionList();

        List<Money> amounts = transactions.stream().map(transaction -> transaction.getAmount()).collect(Collectors.toList());

        Money balance = Money.of(amounts.stream().collect(MonetaryFunctions.summarizingMonetary(Monetary.getCurrency(AppConstants.CURRENCY_CODE))).getSum().getNumber(), AppConstants.CURRENCY_CODE).negate();

        response.setBalance(balance);
        response.setNumberOfTxns(transactions.size());

        logger.info("checkBalance - END - responding for: "+ request.getFromAccount());

        //Fine is like DEBUG for slf4j
        logger.fine("checkBalance - TransactionResponse : "+ response.toString());

        return response;

    }

    public TransactionListResponse getCreditedTransactions(TransactionRequest request) throws BusinessException {

        logger.info("getCreditedTransactions - request for: "+ request.getFromAccount());

        TransactionListResponse response = new TransactionListResponse();
        dataRepository = DataRepository.getInstance();

        List<Transaction> transactions = dataRepository.getTransactions(request);

        List<String> reverseTransactionIdsa = getReversalTransactions(request).getTransactionList().stream()//.map(x -> x.getRelatedTxn())      //Stream<Set<String>>
                .flatMap(x -> Stream.of(x.getRelatedTxn()))   //Stream<String>
                .distinct()
                .collect(Collectors.toList());

        transactions = request.getWithReversal()
                        ? transactions.stream().filter(txn -> !reverseTransactionIdsa.contains(txn.getTxnId())).collect(Collectors.toList())
                        :transactions;

        response.setTransactionList(transactions);
        logger.info("getCreditedTransactions - END - responding for: "+ request.getFromAccount());
        logger.fine("getCreditedTransactions - TransactionResponse : "+ response.toString());

        return response;
    }

    public TransactionListResponse getReversalTransactions(TransactionRequest request) throws BusinessException {

        logger.info("getReversalTransactions - request for: "+ request.getFromAccount());

        TransactionListResponse response = new TransactionListResponse();
        dataRepository = DataRepository.getInstance();

        List<Transaction> transactions = dataRepository.getReversalTransactions(request);

        for(Transaction transaction: transactions){
            try {
                Transaction getTransaction = dataRepository.getTransactionsById(transaction.getRelatedTxn());

                if (!getTransaction.getFromAccount().equalsIgnoreCase(transaction.getFromAccount())
                        || !getTransaction.getToAccount().equalsIgnoreCase(transaction.getToAccount())) {
                    throw new BusinessException("Invalid Reversal relatedTxnId, mistmatch account numbers: " + transaction.getTxnId(), "ERR_TXN02");
                }
            } catch (java.util.NoSuchElementException e) {
                throw new BusinessException("Reversal relatedTxnId does not exist: " + transaction.getTxnId(), "ERR_TXN01");

            }

        }

        response.setTransactionList(transactions);
        logger.info("getReversalTransactions - END - responding for: "+ request.getFromAccount());
        logger.fine("getReversalTransactions - TransactionResponse : "+ response.toString());

        return response;
    }
}
