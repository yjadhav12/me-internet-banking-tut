package com.group.internet.banking.mains;

import com.group.internet.banking.exception.BusinessException;
import com.group.internet.banking.model.Transaction;
import com.group.internet.banking.model.TransactionRequest;
import com.group.internet.banking.model.TransactionResponse;
import com.group.internet.banking.service.TransactionService;
import com.group.internet.banking.util.CsvReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class InternetBankingApplication {

    private List<Transaction> transactions;

    private TransactionService transactionService;

    public static void main(String args[]) throws ParseException, BusinessException {

        List<Transaction> transactions = CsvReader.readFromCSV("src/main/resources/mockData.csv");

        // let's print all the read from CSV file
        for (Transaction b : transactions) {
            System.out.println(b);
        }


        Scanner console = new Scanner(System.in);

        boolean keepRunning = true;
        while (keepRunning)
        {
            System.out.print("Please provide input or type 'exit' to terminate the program or ENTER to continue: \n");
            keepRunning = console.nextLine().equalsIgnoreCase("exit") ? false : true;

            if (!keepRunning)
            {
               break;
            }
            else
            {
                System.out.println("Please provide accountId: " );
                String fromAccount =  console.nextLine();
                System.out.println("Please provide From Date");
                String fromDate =  console.nextLine();
                System.out.println("Please provide To Date ");
                String toDate =  console.nextLine();

                TransactionRequest transactionRequest =  new TransactionRequest();
                transactionRequest.setFromAccount(fromAccount);
                transactionRequest.setFromTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse(fromDate));
                transactionRequest.setToTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse(toDate));

                TransactionResponse transactionResponse = new InternetBankingApplication().getTransactionService().checkBalance(transactionRequest);

                System.out.println("Hello " + transactionResponse.getBalance());



            }
        }
    }

    TransactionService getTransactionService(){
        return new TransactionService();
    }


}