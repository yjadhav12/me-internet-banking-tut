package com.group.internet.banking.util;

import com.group.internet.banking.model.Transaction;
import org.javamoney.moneta.Money;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CsvReader {

    public static List<Transaction> readFromCSV(String fileName) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            br.readLine();
            String line = br.readLine();

            while (line != null) {

                String[] attributes = line.split(",");

                //ugly but optimized way to trim elements without new array creation
                attributes = Arrays.stream(attributes).map(String::trim).toArray(String[]::new);

                //Arrays.stream(attributes).map(String::trim).toArray(unused -> attributes);

                Transaction transaction = createTransaction(attributes);

                transactions.add(transaction);

                line = br.readLine();
            }

        } catch (IOException | ParseException ioe) {
            ioe.printStackTrace();
        }

        return transactions;
    }

    private static Transaction createTransaction(String[] metadata) throws ParseException {
        String txnId = metadata[0];
        String fromAcnt = metadata[1];
        String toAcnt = metadata[2];
        Date txnTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse(metadata[3]);
        Money amount = Money.of(BigDecimal.valueOf(Double.parseDouble(metadata[4])), "AUD");
        String txnType = metadata[5];
        String relatedTxn = metadata.length > 6 ? metadata[6] : "";

        // create and return transaction of this metadata
        Transaction txn = new Transaction();
        txn.setTxnId(txnId);
        txn.setFromAccount(fromAcnt);
        txn.setToAccount(toAcnt);
        txn.setTxnTime(txnTime);
        txn.setAmount(amount);
        txn.setTxnType(txnType);
        txn.setRelatedTxn(relatedTxn);
        return txn;
    }
}
